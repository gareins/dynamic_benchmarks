from collections import defaultdict
from matplotlib import pyplot as plt
from numpy import std
import os

data_file = "script/summary/all_measurements.csv"
imgs_folder = "imgs/"
if not os.path.isdir(imgs_folder):
    os.mkdir(imgs_folder)

try:
    with open(data_file, "r") as fp:
        dat = defaultdict(lambda: defaultdict(list))
        fp.readline()
        for line in fp:
            split_line = fp.readline().split(",")
            if len(split_line) < 9:
                continue
            
            bench, lang, _, n, _, cpu, mem, status, *load, elapsed = split_line
            dat[bench][lang].append((int(n), float(cpu), float(mem), float(elapsed)))

        for meas, mdata in dat.items():
            plt.clf()
            plt.title(meas)

            y_min = 1e10
            y_max = 0
            x_min = 1e10
            x_max = 0
            
            XY = []
            for lang in sorted(mdata.keys()):
                lst = mdata[lang]
                D = defaultdict(list)
                for tpl in lst:
                    D[tpl[0]].append(tpl[3])

                x = list(sorted(D.keys()))
                y = [sum(D[i]) / len(D[i]) for i in x]
                y_err = [std(D[i]) for i in x]

                x_min = min(x) if min(x) < x_min else x_min
                x_max = max(x) if max(x) > x_max else x_max
                y_min = min(y) if min(y) < y_min else y_min
                y_max = max(y) if max(y) > y_max else y_max

                XY.append((x, y, lang))
                plt.errorbar(x, y, yerr=y_err, fmt='-', label=lang)

            plt.legend(loc='upper left', shadow=True, fontsize='medium')
            plt.ylabel("Execution time in seconds")
            plt.xlabel("Problem size")
            x_min, x_max = x_min - (x_max - x_min) / 20, x_max + (x_max - x_min) / 20
            y_min, y_max = y_min - (y_max - y_min) / 20, y_max + (y_max - y_min) / 20

            plt.grid(True)
            plt.axis((x_min, x_max, y_min, y_max))
            plt.savefig(imgs_folder + meas + ".svg")
            
            plt.clf()
            for x, y, lang in XY:
                plt.semilogy(x, y, label = lang)
                
            plt.grid(True)
            plt.title(meas)
            plt.ylabel("Execution time in seconds")
            plt.xlabel("Problem size")
            plt.legend(loc='upper left', shadow=True, fontsize='medium')
            plt.savefig(imgs_folder + meas + "_log.svg")
                

except EnvironmentError:
    print(data_file + " not found! Have you run benchmarks yet?")



