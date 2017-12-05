package vjache.lift

/**
 * This class specifies a particular command to lift to arrive to some
 * floor. This is also could be called a goal.
 */
data class LiftCommand(val console: Console, val toFloor: Int)

/**
 * Specifies which console were used to send command to lift.
 * 'External' -- means that button pushed at some floor to call up lift
 * 'Internal' -- means that button pushed from within lift
 */
enum class Console {
    External,
    Internal
}