package vjache.lift

/**
 * vjache.lift.Agenda is a set of current commands to execute by lift.
 * It is used to select a command with 'highest' priority
 * according to some strategy. After command is executed
 * it removed from agenda.
 *
 * Actually it is a classical notion to program reactive agents
 * behaviour, where instead of term 'command' a 'goal' is used
 * and 'reach' instead of 'execute'.
 */
class Agenda {
    private val commands = mutableMapOf<LiftCommand, Long>()

    /**
     * Add command from agenda.
     */
    @Synchronized
    fun add(cmd: LiftCommand) {
        commands.putIfAbsent(cmd, System.currentTimeMillis())
    }

    /**
     * Remove command from agenda.
     */
    @Synchronized
    fun remove(cmd: LiftCommand) {
        commands.remove(cmd)
    }

    /**
     * Pick the command with the least timestamp from specified console.
     *
     * TODO: This call can be optimized by adding an index by timestamp using TreeMap.
     */
    @Synchronized
    fun pickOldest(console: Console) =
            commands.entries.filter { it.key.console == console }.minBy { it.value }?.key

    /**
     * Pick the command for the specified floor number and console.
     *
     * TODO: This call can be optimized by adding an index by floor using HashMap.
     */
    @Synchronized
    fun pickByFloor(console: Console, floor: Int) =
            commands.entries.firstOrNull { it.key.console == console && it.key.toFloor == floor }?.key
}