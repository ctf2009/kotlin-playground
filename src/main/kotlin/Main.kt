import com.ctf.example.functional.sam.runFunctionalSamExample
import com.ctf.example.infix.runInfixExample
import com.ctf.example.receiver.runReceiverExample
import com.ctf.example.recover.runRecoverExample
import com.ctf.example.sealed.runner.runSealedExample

fun main() {

    // Run the Sealed Class Example
    runSealedExample()

    // Run the Recover Example
    runRecoverExample()

    // Run the Functional Sam Example
    runFunctionalSamExample()

    // Run the Receiver Example
    runReceiverExample()

    // Run the Infix Example
    runInfixExample()
}
