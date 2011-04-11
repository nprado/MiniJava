#!/bin/bash

for file in /media/GUIDO/g23/in/*.java; do
	arq=$( basename $file| sed 's/java/out/' )
	java -jar minijava_g23_b.jar "$file" 2>&1 | tee /media/GUIDO/g23/out/$arq # pasta ~/workspace/MiniJava/out/ 
done
