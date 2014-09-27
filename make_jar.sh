#!/bin/bash

cd src
javac Main.java 
jar cfm ../program.jar manifest.mf *.class
rm -f *.class
cd ../

