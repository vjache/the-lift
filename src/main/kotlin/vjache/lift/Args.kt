package vjache.lift

/**
 * The arguments of a program.
 */
data class Args(var speedMeterPerSec: Float = 1f,
                var floors: Int = 20,
                var floorHeightMeters: Float = 2.5f,
                var openCloseTimeSec: Float = 5f){
    companion object {
        private lateinit var  instance_: Args

        val instance: Args
        get() = instance_

        fun parseArgs(args: Array<String>){
            instance_ = Args()

            args.forEach { it ->
                when{
                    it.startsWith("-s") ->
                        instance_.speedMeterPerSec = it.substring(2).toFloat()
                    it.startsWith("-f") ->
                        instance_.floors = it.substring(2).toInt()
                    it.startsWith("-h") ->
                        instance_.floorHeightMeters = it.substring(2).toFloat()
                    it.startsWith("-o") ->
                        instance_.openCloseTimeSec = it.substring(2).toFloat()
                    else ->
                            throw IllegalArgumentException("Bad argument '$it'.")
                }
            }
        }
    }

    val oneFloorMoveTimeMillis = (floorHeightMeters * 1000 / speedMeterPerSec).toLong()
    val openCloseTimeMillis = (openCloseTimeSec * 1000).toLong()
}

