##ClockWidget

This application is a **vector graphics** clock in **Java**. It contains less than 300 lines and it has many clues on how to use vector graphics in **Java** (for constructing **GUI**s, for example). It has a small thread to refresh and update the clock needles positions according to the real-time system clock. It positions itself at the top right side of the screen. It's scale and other parameters can be easily changed in the source code, requiring recompilation and .jar generation. If you are interested in writing a cmake file to compile this program, please let me know.

###`Clock` class

The clock is defined in the class `Clock`. Its geometry is defined by a series of private variables in the begining of the class. The circular parts are defined with `Ellipse2D` primitives. The needles are defined by `Path2D` primitives.

###Paths

Every _shape_ in **Java** is defined by _paths_ which are the lowest level way to define _shape_ s in vectors graphics in **Java**. `Path2D` primitive from **Java awt**, is the most basic way a vector graphics _shape_ can be defined. A _path_ can be composed by possibly 5 basic commands: `moveTo`, `lineTo`, `quadTo`, `curveTo` and `closePath`. The meaning of each these commands can be found at the end of this page.

The different explicit _paths_ in the class `Clock` are defined by the functions: `hour_mark()`, `minute_mark()`, `h_needle()`, `m_needle()` and `s_needle()`.

The functions `hour_mark()` and `minute_mark()` define the static distinctives marks indicating the numbers around the clock dial. The function `hour_mark()` is used to represent the numbers 12, 3, 6 and 9. The function `minute_mark()` is used to represent the numbers 1, 2, 4, 5, 7, 8, 10 and 11.

The functions `h_needle()`, `m_needle()` and `s_needle()`, define the dynamic elements of the clock, the needles that move according to the system real-time clock, respectively representing the _hour needle_ (the shorter needle pointing to the current hour), the _minute needle_ (the longuer needle indicating the minutes) and the _second needle_ (the thinner faster needle representing the seconds).

###Areas

An `Area` in Java is a higher level concept where a _shape_ can be defined by composing other `Areas` with **CSG** operators such as: `add` (union), `subtract` and `intersect`. An exemple how to use these operators can be found at https://docs.oracle.com/javase/tutorial/2d/advanced/complexshapes.html. Intially, each basic `Area` component of final obtained _shape_ must be initialized with a _shape_. This is shown in the functions: `downShadowArea()`, `dialRimArea()`, and `dialArea()`. The resulting `Area` is the final shape to be displayed.

### Interrupting `Thread`

As mentioned previously, it's the system real-time clock that gives the actual time to be displayed by the clock. In terms of **Java** programming this is done by using a `Calendar` and getting the actual hour, minute and second. However, in order to update the clock at every second, a `Thread` is used. This is implemented by the functions `start()`, `stop()` and `run()`. Once the `Thread` is started the `run` function is executed. This function is just a loop that makes the thread `sleep` for one second and that redisplays the clock (`repaint()`) right after.

###The function `paintComponent()`

This function is the heart of the `Clock` class. It is actually a function from `JPanel` that is overloaded here. `JPanel` is the type that `Clock` is inherited from. The function `paintComponent()` is called every time the `repaint()` function is called in the `Thread` described previously.

Besides reading the current time from `Calendar`, it also executes all the commands required to draw the clock and the current position of the needles. The heuristics for converting the hour, minute and second into angles are shown in this function. Since hours and minutes are naturally expressed in base 60, they actualy represent their angles in degrees which is then converted to radians for display. The hour is given in base 12, which is also thought as an angle in degrees, then converted to radians.

The most customizable feature of the whole program is probably the ability to enlarge the clock without any quality degradation. This can be easily managed changing the scale in this function. Currently, the scale is set to a quart, that is, `g.scale(0.25f,0.25f)`. One can enlarge it by just increasing these values, or reduce the size by decreasing these values. When changing the scale, one also needs to change the variable `size` in `ClockWidget` class, so the clock can be nicely adjusted to right side of the screen. Currently this value is set to `112`. For simplicity, an automatic size adjustment was not coded, but it can be programmed. Currently, the value of `size` must be calculated by hand. For example, if scale is changed to 2, value of `size` must be updated to `896`, that is, `112 x 8 = 896`, since the scale was multiplied by 8.

###`Path` Commands

a `Path` can contain the following commands:

1. **moveto(x,y)**
This commands establishes a new current point. The effect is as if the "pen" were lifted and moved to a new location. A path data segment (if there is one) must begin with a `moveto` command. Subsequent `movet`" commands (i.e., when the `moveto` is not the first command) represent the start of a new subpath.
When it is executed `moveto` starts a new sub-path at the given (x,y) coordinate. A `moveto` indicates that absolute coordinates will follow.
2. **lineto(x,y)**
Draws a straight line from the current point to a new point at the given (x,y) coordinate.
3. **quadTo(x1,y1,x2,y2,x3,y3) 
A quadratic Bézier segment is defined by a start point (x1,y1), an end point (x3,y3), and one control point (x2,y2).



