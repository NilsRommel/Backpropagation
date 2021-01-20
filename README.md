# Backpropagation
This Programm use the Backpropagation Algorithm to identify handwritten Numbers

The Neural Network use the Gray-Values of an images as Input-Vector. The Input-Vector goes forward through all Layers. The ouput is also a one-dimensional vector.
It is intended to show the User how likely his handwritten Nummber is a one or a two.

# Converting the Pictures

All Pictures are safed in the files: Bilder and TestBilder. The Images in the "Bilder" file are for Training. The other Images in "TestBilder" are the User Input.
The main Goal is to implement the MNIST database or Iris Data Set.

The Programm (class = PictureAnalyse) convert the Image in an one-dimensional Array with all Gray-Values of each Pixel.
The Gray Value = 0.299 * Red + 0.587 * Green + 0.114 * Blue of one Pixel. Red,Green and Blue are Numbers between 0 and 255. For the Network will the Gray Value be divided by 255 to have a number between 1 and 0. All png-files are stored in this way that the first Number over there Names is the Value of that written Nummber. 

# How the Neural Network works




(All Calculations are based on: https://lmb.informatik.uni-freiburg.de/lectures/old_lmb/mustererkennung/WS0506/08_b_ME.pdf and https://de.wikipedia.org/wiki/Backpropagation)
