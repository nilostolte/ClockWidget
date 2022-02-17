## ClockWidget

This application is a **vector graphics** clock in **Java**. It contains less than 300 lines and it has many clues on how to use vector graphics in **Java** (for constructing **GUI**s, for example). It has a small thread to refresh and update the clock needles positions according to the real-time system clock. It positions itself at the top right side of the screen. It's scale and other parameters can be easily changed in the source code, requiring recompilation and .jar generation. If you are interested in writing a cmake file to compile this program, please let me know.

### `Clock` class

The clock is defined in the class `Clock`. Its geometry is defined by a series of private variables in the begining of the class. The circular parts are defined with `Ellipse2D` primitives. The needles are defined by `Path2D` primitives.

### Paths

Every _shape_ in **Java** is defined by _paths_ which are the lowest level way to define _shapes_ in vectors graphics in **Java**. `Path2D` primitive from **Java awt**, is the most basic way a vector graphics _shape_ can be defined. A _path_ can be composed by possibly 5 basic commands: `moveTo`, `lineTo`, `quadTo`, `curveTo` and `closePath`. The meaning of each these commands can be found [below](https://github.com/nilostolte/ClockWidget/blob/main/README.md#path-commands).

The different explicit _paths_ in the class `Clock` are defined by the functions: `hour_mark()`, `minute_mark()`, `h_needle()`, `m_needle()` and `s_needle()`.

The functions `hour_mark()` and `minute_mark()` define the static distinctives marks indicating the numbers around the clock dial. The function `hour_mark()` is used to represent the numbers 12, 3, 6 and 9. The function `minute_mark()` is used to represent the numbers 1, 2, 4, 5, 7, 8, 10 and 11.

The functions `h_needle()`, `m_needle()` and `s_needle()`, define the dynamic elements of the clock, the needles that move according to the system real-time clock, respectively representing the _hour needle_ (the shorter needle pointing to the current hour), the _minute needle_ (the longuer needle indicating the minutes) and the _second needle_ (the thinner faster needle representing the seconds).

### Areas

An `Area` in Java is a higher level concept where a _shape_ can be defined by composing other `Areas` with **CSG** operators such as: `add` (union), `subtract` and `intersect`. An exemple how to use these operators can be found [here](https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html). Initially, each basic `Area` component of final obtained _shape_ must be initialized with a _shape_. This is shown in the functions: `downShadowArea()`, `dialRimArea()`, and `dialArea()`. The resulting `Area` is the final shape to be displayed.

### Interrupting `Thread`

As mentioned previously, it's the system real-time clock that gives the actual time to be displayed by the clock. In terms of **Java** programming this is done by using a `Calendar` and getting the actual hour, minute and second. However, in order to update the clock at every second, a `Thread` is used. This is implemented by the functions `start()`, `stop()` and `run()`. Once the `Thread` is started the `run` function is executed. This function is just a loop that makes the thread `sleep` for one second and that redisplays the clock (`repaint()`) right after.

### The function `paintComponent()`

This function is the heart of the `Clock` class. It is actually a function from `JPanel` that is overloaded here. `JPanel` is the type that `Clock` is inherited from. The function `paintComponent()` is called every time the `repaint()` function is called in the `Thread` described previously.

Besides reading the current time from `Calendar`, it also executes all the commands required to draw the clock and the current position of the needles. The heuristics for converting the hour, minute and second into angles are shown in this function. Since hours and minutes are naturally expressed in base 60, they actualy represent their angles in degrees which is then converted to radians for display. The hour is given in base 12, which is also thought as an angle in degrees, then converted to radians.

The most customizable feature of the whole program is probably the ability to enlarge the clock without any quality degradation. This can be easily managed changing the scale in this function. Currently, the scale is set to a quart, that is, `g.scale(0.25f,0.25f)`. One can enlarge it by just increasing these values, or reduce the size by decreasing these values. When changing the scale, one also needs to change the variable `size` in `ClockWidget` class, so the clock can be nicely adjusted to right side of the screen. Currently this value is set to `112`. For simplicity, an automatic size adjustment was not coded, but it can be programmed. Currently, the value of `size` must be calculated by hand. For example, if scale is changed to 2, value of `size` must be updated to `896`, that is, 112 x 8 = 896, since the scale went from 0.25 to 2, that is, one multiplies `size` by 8.

### `Path` Commands

A `Path`can be added (or it can already contain) segments defined with the following commands:

1. **moveTo(x,y)**<br>
This command establishes a new current point. The effect is as if the "pen" were lifted and moved to a new location. A path data segment (if there is one) must begin with a `moveTo` command. Subsequent `moveTo` commands (i.e., when the `moveTo` is not the first command) represent the start of a new subpath.
When it is executed `moveTo` starts a new sub-path at the given (x,y) coordinate. A `moveTo` indicates that absolute coordinates will follow.
2. **lineTo(x,y)**<br>
This command establishes a straight line from the current point to a new point at the given (x,y) coordinate.
3. **quadTo(x1,y1,x2,y2)**<br>
This command establishes a quadratic Bézier segment that is defined by a start point in the current point, an end point (x2,y2), and one control point (x1,y1).
4. **curveTo(x1,y1,x2,y2,x3,y3)**<br>
This command establishes a cubic Bézier segment that is defined by a start point in the current point, an end point (x3,y3), and two control points (x1,y1) and (x2,y2).
5. **closePath()**<br>
Ends the current subpath and causes an automatic straight line to be drawn from the current point to the initial point of the current subpath. If a `closePath()` is followed immediately by a `moveTo`, then the `moveTo` identifies the start point of the next subpath

### Vector Graphics For GUI Programming

**ClockWidget** illustrates how complete Graphics User Interfaces can be designed in a similar manner that this code was designed and programmed. In the case of this widget, the interactivity has been substituted by a thread that animates the needles instead of the user interaction.

In addition, all the GUI design can be defined by only these primitives and the commands to display them. Thus, the whole GUI design is machine independent and could be stored as data as done with **AdobeXD**. Ideally, if **AdobeXD** had a text format storage language, this software could be used to design the interfaces and the generated file could be used directly as the GUI design.

For even more portability, a language to define the interactions is desirable. In this way, the interface could be even changed on the fly (with the program running) and it can work not only in different operating systems but also with different programming languages.

This approach for designing and creating GUI is not something conceptually new, since it is the base of [Quartz](https://en.wikipedia.org/wiki/Quartz_(graphics_layer)), which is what is behind the Core Graphics framework in Apple's macOS operating system. It is possible that the intrinsic vector graphics features in **Java awt** (path rendering, vector fonts, etc.) might be a part of [NeWs](https://en.wikipedia.org/wiki/NeWS), a vector graphics Windowing System developed by Sun Microsystems, the same company that created [**Java**](https://en.wikipedia.org/wiki/Java_(programming_language)). At least, **awt vector graphics** have strong ressemblance to NeWs and to [PostScript](https://en.wikipedia.org/wiki/PostScript).

In recent years, vector graphics had a return with [**OpenVG**](https://en.wikipedia.org/wiki/OpenVG) an extensive vector graphics framework with hardware acceleration that can be used in **C/C++**. Although, **OpenVG** is mainly just a standard API definition which is used by many companies to implement their own **OpenVG** compatible software. [**NanoVG**](https://github.com/memononen/nanovg), a public domain vector graphics library, is a far much lighter library and can be used to make interfaces developed for **Java**, in the way claimed here, that are potentially directly portable in **C/C++** language. This has the main advantage of better performance, since **C/C++** is compiled directly into machine code and that hardware acceleration is possible depending on the machine.

**NanoVG** provides support for vector fonts but it requires the original font to be read. This can be a tremendous drawback, particularly in embedded applications. Not only reading several fonts in the same application can lead to lagging, but it can even be inappropriate in embedded environments, for example, having no file system.

The project [**π Vector GUI for Java and Android**](https://github.com/nilostolte/Projects-Presentations/blob/main/%CF%80%20Vector%20GUI%20for%20Java%20and%20Android.md#%CF%80-vector-gui-for-java-and-android) uses a quite different strategy. The fonts are read in a pre-processing stage and converted into a **Java** class (can be modified to generate a **C/C++** class). This method is more robust and totally standalone, completely machine independent. This **Java** (or **C/C++**) class contains compact font information (Glyphs, their width and the kerning pairs) that allow displaying (with antialiasing activated) fonts as they would be displayed by a font renderer, but in a much more compact and faster way. A public domain version of this conversion software for **Truetype** fonts is going to be soon delivered in this GitHub repository. Currently, the glyphs are defined by `Path2D` paths which are initialiazed dynamically. It is also possible to represent paths with preexisting arrays using [`GeneralPath`](https://docs.oracle.com/javase/7/docs/api/java/awt/geom/GeneralPath.html). In **C/C++** environments, static arrays can be stored directly in the executable code. In this case the path initialization is practically done in the program loading time (transfering from secondary memory to the main memory).

The problem of fonts can be dealt in many different ways depending on the context. In the project **π Vector GUI for Java and Android**, fonts are only used when it is impossible to know in advance the text that is going to be displayed. The typical case is when the user enters a text into an application or if a text (a book, for example) is read. Another case is when system errors are caught and the their error messages must be shown. In GUI, texts are much more static entities and can be dealt in their vectorized form. Vectorizations can be obtained by a number of different ways. The project **π Vector GUI for Java and Android** gives many examples. The case of the vector banner for the project is a good example of text vectorization converted to EMF. The convertion from PDF to EMF does not preserve the fonts. First the text must be vectorized (as explained there), reconverted to PDF, and finally converted to EMF to be used in PowerPoint.

But nowadays this could have been done with [Java2PPT](https://github.com/nilostolte/Java2PPT) to convert Java vector graphics to PowerPoint using [MicroVBA](https://github.com/nilostolte/MicroVBA-PowerPoint) as an intermediate language. This language is then interpreted by our MicroVBA interpreter inside PowerPoint. In this way we avoid the limitations of VBA.
