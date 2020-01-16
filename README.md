# Kindle my clippings

The Clippings class allows you to parse the 'My Clippings.txt' file containing your kindle highlights.
The input is the aforementioned file, while the output is a file per book, containing the numbered highlights.
If your My Clippings.txt file contains highlights from more than one book, then the output is more than one file.

## How to run
Run the Clippings.java main method with a program arg pointing to the location where the clippings file is stored.
The program args can be configured in the Intellij launcher, in the "Program arguments" field.

Example:
```
"/home/user/My Clippings.txt"
```
 
If the class is exported as a jar then pass the arguments to the main function similar to:

```
java [ options ] -jar clippings.jar "/home/user/My Clippings.txt"
```