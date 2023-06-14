# Simple Java Instrument Tuner

This is a personal project made a few years ago (roughly October 2021) where 
I tried to create a simple Tuner for my bass and electric guitar.

To do this, I tried to combine my little knowledge in Java GUIs and the Processing
language / library with my non-existing knowledge in digital signal processing
by experimenting with the "TarsosDSP" library.

## Dependencies:
- the "core.jar" file from the [Processing](https://processing.org/download) library --> download the .zip or .tgz file,
unpack it where you want, and then you should find the "core.jar" file in core/library/core.jar; copy it
and place in this project's "external-libs" folder (if it doesn't exist create it in the root folder, where the "src" folder is)
- the "TarsosDSP-2.4.jar" from [TarsosDSP](https://github.com/JorenSix/TarsosDSP) library --> open the GitHub link, in
the first lines of the README there should be a link to [TarsosDSP release directory](https://0110.be/releases/TarsosDSP/),
download the TarsosDSP-2.4 version, and place the .jar file in this project's "external-libs" folder 
(if it doesn't exist create it in the root folder, where the "src" folder is)

after that, open this project with IntelliJ (Eclipse should work too) and run the class "TunerGUI in the src/view" directory;
the library should get automatically loaded and builded and the program should be able to start without errors (hopefully!).

