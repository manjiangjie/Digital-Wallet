#!/usr/bin/env bash

# example of the run script for running the fraud detection algorithm with a python file,
# but could be replaced with similar files from any major language

# I'll execute my programs, with the input directory paymo_input and output the files in the directory paymo_output
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd src
javac User.java
javac Antifraud.java
java Antifraud $DIR/paymo_input/batch_payment.txt $DIR/paymo_input/stream_payment.txt $DIR/paymo_output/output1.txt $DIR/paymo_output/output2.txt $DIR/paymo_output/output3.txt
