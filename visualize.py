from collections import defaultdict
from matplotlib import pyplot as plt
from matplotlib.ticker import FixedLocator
from numpy import std
import os

def decorate_plot(axis, ylabel, xlabel, title, xticks):
    plt.title(title)
    axis.legend(loc='upper left', shadow=True, fontsize='medium')
    plt.ylabel(ylabel)
    plt.xlabel(xlabel)
    axis.xaxis.set_major_locator(FixedLocator(xticks))
    axis.grid(True)


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
            fig = plt.figure()
            ax = fig.add_subplot(111)   

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
                ax.errorbar(x, y, yerr=y_err, fmt='-', label=lang)

            x_min, x_max = x_min - (x_max - x_min) / 20, x_max + (x_max - x_min) / 20
            y_min, y_max = y_min - (y_max - y_min) / 20, y_max + (y_max - y_min) / 20
            
            ax.axis((x_min, x_max, y_min, y_max))
            decorate_plot(ax, "Problem size", "Execution time in seconds", meas, XY[0][0])
            plt.savefig(imgs_folder + meas + ".svg")
            
            plt.clf()
            ax = fig.add_subplot(111) 
            for x, y, lang in XY:
                ax.semilogy(x, y, label = lang)
            
            decorate_plot(ax, "Problem size", "Execution time in seconds", meas, XY[0][0])
            plt.savefig(imgs_folder + meas + "_log.svg")
                

except EnvironmentError:
    print(data_file + " not found! Have you run benchmarks yet?")



