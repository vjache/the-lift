package vjache.lift

/**
 * A vjache.lift.Lift. It is a runnable entity which executes its state machine against agenda.
 */
class Lift : Runnable {

    /**
     * vjache.lift.Agenda of current commands.
     */
    private val agenda = Agenda()

    /**
     * Current state. Initial state is a 'rest' on 0 floor.
     */
    private var state: State = RestState(0)

    /**
     * Push button on external console to call up a lift.
     */
    @Synchronized
    fun pushExternalButton(destFlour: Int) = agenda.add(LiftCommand(Console.External, destFlour))

    /**
     * Push button on a console inside a lift.
     */
    @Synchronized
    fun pushInternalButton(destFlour: Int) = agenda.add(LiftCommand(Console.Internal, destFlour))

    /**
     * Run state machine.
     */
    override fun run() {
        while (true)
            state = state.transition(agenda)
    }

}