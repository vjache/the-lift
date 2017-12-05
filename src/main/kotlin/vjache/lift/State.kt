package vjache.lift

/**
 * A generic state of a lift. This class and its subclasses
 * define a state machine of a lift.
 */
abstract class State(val startFloor: Int) {
    open val endFloor = startFloor
    abstract fun transition(agenda: Agenda) : State

    /**
     * This is a default strategy which "prefers" the earliest(oldest)
     * and internal (push inside lift) command to lift, but if no such a
     * command then tries to pick earliest external command (i.e. somebody calling up lift).
     *
     * In other words the man who is in lift has a priority over those who are awaiting on floors.
     */
    protected fun defaultTransition(agenda: Agenda): State {
        // Firstly try pick 'oldest' & 'internal' command, if 'null' then try the same for 'external'
        val cmd = agenda.pickOldest(Console.Internal) ?: agenda.pickOldest(Console.External)
        // Create state
        return if (cmd != null) {
            when {
                cmd.toFloor < endFloor -> MoveDownState(currentFloor = endFloor)
                cmd.toFloor > endFloor -> MoveUpState(currentFloor = endFloor)
                //cmd.toFloor == endFloor
                else -> OpenCloseDoorState(currentFloor = endFloor)
            }
        } else {
            RestState(currentFloor = endFloor)
        }
    }

}

/**
 * This is a state when lift moves one floor down.
 */
class MoveDownState(currentFloor: Int) : State(currentFloor) {
    override val endFloor: Int
        get() = startFloor - 1

    override fun transition(agenda: Agenda): State {
        Thread.sleep(Args.instance.oneFloorMoveTimeMillis)
        println("- $endFloor ")
        //
        return if (agenda.pickByFloor(Console.Internal, endFloor) != null) {
            OpenCloseDoorState(currentFloor = endFloor)
        } else {
            defaultTransition(agenda)
        }
    }
}

/**
 * This is a state when lift moves one floor up.
 */
class MoveUpState(currentFloor: Int) : State(currentFloor) {
    override val endFloor: Int
        get() = startFloor + 1

    override fun transition(agenda: Agenda): State {
        Thread.sleep(Args.instance.oneFloorMoveTimeMillis)
        println("+ $endFloor ")
        //
        val cmd = agenda.pickByFloor(Console.Internal, endFloor) ?: agenda.pickByFloor(Console.External, endFloor)
        return if (cmd != null) {
            OpenCloseDoorState(currentFloor = endFloor)
        } else {
            defaultTransition(agenda)
        }
    }
}

/**
 * This is a state when lift is idle and awaiting at
 * least one command appear in an agenda.
 */
open class RestState(currentFloor: Int) : State(currentFloor) {

    override fun transition(agenda: Agenda): State {
        Thread.sleep(100)
        return defaultTransition(agenda)
    }
}

/**
 * This is a state when lift stopped at some floor and opened
 * doors and after some time closes them. It is supposed that
 * doors opened and closed instantly but there is a delay between
 * opened and close.
 */
class OpenCloseDoorState(currentFloor: Int) : RestState(currentFloor) {
    override fun transition(agenda: Agenda): State {
        println("Opened $endFloor ...")
        Thread.sleep(Args.instance.openCloseTimeMillis)
        println("Closed $endFloor.")
        agenda.pickByFloor(Console.Internal, endFloor)?.let { it ->
            agenda.remove(it)
        }
        agenda.pickByFloor(Console.External, endFloor)?.let { it ->
            agenda.remove(it)
        }
        return defaultTransition(agenda)
    }
}