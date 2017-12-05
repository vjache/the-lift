package vjache.lift

/**
 * Entry point.
 */
fun main(args: Array<String>) {

    // Init arguments of a program
    Args.parseArgs(args)

    if (args.isEmpty()) {
        println("Hello! This is a Lift simulator.")
        println("The usage is like: ")
        println("\tjava -jar the-lift<VSN>.jar -s2 -f10 -h2.5 -o5")
        println("where:")
        println("\t-sFLOAT  -- lift speed in meters pers second")
        println("\t-fINT    -- the number of floors")
        println("\t-hFLOAT  -- the height of floor")
        println("\t-oFLOAT  -- the time delay between open and close doors\n")
        println("It is launched with no args so default will be used:")
        println("\t${Args.instance}")
    }
    else {
        println("Arguments parsed:")
        println("\t${Args.instance}")
    }

    println("Lift is in rest at 0 floor. Enter bellow e.g. 'e0'.")

    // Create lift
    val lift = Lift()

    // Start lift
    Thread(lift).start()

    // Read first line
    var line = readLine()

    while (line != null) {
        try {

            when {
                line.startsWith("e") -> {
                    val floor = parseFloor(line)
                    lift.pushExternalButton(floor)
                }
                line.startsWith("i") -> {
                    val floor = parseFloor(line)
                    lift.pushInternalButton(floor)
                }
                line.startsWith("q") -> System.exit(0)
                else -> println("What?")
            }

        } catch (e: NumberFormatException) {
            println("Push external/internal button syntax -- (i|e)NUMBER, e.g.: i5 or e3")
        }

        line = readLine()
    }
}

private fun parseFloor(line: String) = line.substring(1).toInt()

