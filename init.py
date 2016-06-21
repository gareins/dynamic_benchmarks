import os
import shutil
import random
import urllib.request
import zipfile

script_dir = "script/"
programs_dir = "programs/"

def copy_programs():
    add_implementation = {
        "python3": "pypy3",
        "lua": "luajit",
        "node": "js"
    }

    dir_out = script_dir + "programs/"
    if os.path.isdir(dir_out):
        shutil.rmtree(dir_out)
    os.mkdir(dir_out)

    for d in os.listdir(programs_dir):
        din = programs_dir + d + "/"
        dout = dir_out + d + "/"

        if not os.path.isdir(din):
            continue

        if not os.path.isdir(dout):
            os.mkdir(dout)

        for program in os.listdir(din):
            shutil.copy(din + program, dout + program)
            
            extension = program.split(".")[-1]
            if extension in add_implementation:
                shutil.copy(din + program, dout + program.replace(extension, add_implementation[extension]))
                

if __name__ == "__main__":
    tmp_zip_file = ".tmp.zip"
    print("Downloading...")
    urllib.request.urlretrieve("http://benchmarksgame.alioth.debian.org/download/benchmarksgame-script.zip", tmp_zip_file)
    
    if not os.path.isdir(script_dir):
        os.mkdir(script_dir)
    shutil.rmtree(script_dir)
    
    print("Extracting...")
    with zipfile.ZipFile(tmp_zip_file, "r") as z:
        z.extractall(script_dir)
    os.remove(tmp_zip_file)
    
    print("Loading programs and ini")
    shutil.copy("my.linux.ini", "script/makefiles/my.linux.ini")
    copy_programs()
    
    print("\nDONE\n")
    print("Run programs with $python2 script/bin/bencher.py")
    print("View raw results in script/summary")
    print("generate images with $python3 visualize.py")
    

