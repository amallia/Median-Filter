#SPM 2015 Project
##Median Filter - Multi-core Implementation

This software is the parallel implementation of a median filter over images. Realized in two different patterns and two implementations for each one. Automatic tests have been realized to obtain performance parameters. Results in terms of performance are in line with the expectation. Future improvements are possible. 

###Output Example
![Original Noisy Image](https://github.com/amallia/Median-Filter/raw/master/examples/median-filter-denoised.png)
![Median Filtered Image](https://github.com/amallia/Median-Filter/raw/master/examples/original-noisy.png)


###Report
A report which describe in details the project can be found [here](https://github.com/amallia/Median-Filter/raw/master/SPM%20-%20Relazione.pdf). 

###Usage
```
git clone git@github.com:amallia/Median-Filter.git
cd Median-Filter
ant
java –jar dist/MedianFilter.jar
```
The above command will show the following helper menu.
```
__  __          _ _             _____ _ _ _
|  \/  | ___  __| (_) __ _ _ __ |  ___(_) | |_ ___ _ __
| |\/| |/ _ \/ _` | |/ _` | '_ \| |_  | | | __/ _ \ '__|
| |  | |  __/ (_| | | (_| | | | |  _| | | | ||  __/ |
|_|  |_|\___|\__,_|_|\__,_|_| |_|_|   |_|_|\__\___|_|
usage: MedianFilter -d <200,400,1000...> [-h] -s <1,10,50...> -t <4>
 -d,--dim <200,400,1000...>   matrix dimension in pixel
 -h,--help                    print help message
 -s,--stream <1,10,50...>     number of matrices to test
 -t,--threads <4>             threads number
```
*	-d, --dim : it allows defining the sizes of the (square) matrices generated for the tests. Several sizes can be provided using a comma separated format
*	-s, --stream : it allows defining the number of matrices in the input stream. Several stream numbers can be provided using a comma-separated format.
*	-t, --thread : it allows specifying the maximum number of threads to use.
