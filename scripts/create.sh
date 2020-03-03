#!/bin/bash

python -m http.server &
PID=$!

cd $1

NAME="$(cut -d'/' -f2<<<"$1")"
echo $NAME
NR="$(cut -d'-' -f1<<<"$NAME")"
echo $NR

echo "Generate PDF for $NAME...=> $NR-Slides.pdf"
decktape remark http://localhost:8000/slides.html?$NAME $NR-Slides.pdf
echo ".. done!"

cd ../..

kill $PID
