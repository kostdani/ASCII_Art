# ASCII Art
The idea of this project is to load images, translate them into ASCII ART images, optionally apply filters, and save them to a file or print them to the console.

## Usage
The program is run with the command like following:
    ./run --image "../images/test-image.jpg" --rotate +90 --scale 0.25 --invert --output-console
All commands are worked through sequentially one by one.
Possible commands:
* --image path: loads image
* --random-image: generates random image with dimensions 10-30
* --table {bourke|bourke-small}:select one of bourke conversion tables
* --custom-table seq: use seq as conversion table
* --output-console: rites image out to console
* --output-file path: rites image out to file path
* --scale n: scale to n times
* --invert: inverts image
* --{horizontal|vertial}-flip: flips image
* --help writes this help info out
