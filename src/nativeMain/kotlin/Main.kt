import com.funkatronics.kborsh.Borsh
import kotlinx.serialization.Serializable
import com.solana.publickey.SolanaPublicKey
import com.solana.publickey.ProgramDerivedAddress
import com.solana.serialization.AnchorInstructionSerializer
import com.solana.transaction.AccountMeta
import com.solana.transaction.TransactionInstruction
import kotlinx.coroutines.runBlocking
//import com.solana.rpc.core.RpcClient
//import com.solanamobile.rpc.core.network.KtorNetworkDriver

@Serializable
class Args_increment(val amount: UInt)

fun main() {


//    // Fetch latest blockhash from RPC
//    val rpcClient = SolanaRpcClient("https://api.devnet.solana.com", KtorNetworkDriver())
//    val blockhashResponse = rpcClient.getLatestBlockhash()

    val programId = SolanaPublicKey.from("ADraQ2ENAbVoVZhvH5SPxWPsF2hH5YmFcgx61TafHuwu")

    // Counter account has a single seed 'counter'
    val seeds = listOf("counter".encodeToByteArray())

    runBlocking {

        //// Calculate the PDA
        val result = ProgramDerivedAddress.find(seeds, programId)

        print(result)

        //// Unwrap the result
        val counterAccountPDA = result.getOrNull()

        val incrementAmount = 5
        val encodedInstructionData = Borsh.encodeToByteArray(
            AnchorInstructionSerializer("increment"),
            Args_increment(incrementAmount.toUInt())
        )

        val incrementInstruction = TransactionInstruction(
            SolanaPublicKey.from("ADraQ2ENAbVoVZhvH5SPxWPsF2hH5YmFcgx61TafHuwu"),
            listOf(AccountMeta(counterAccountPDA!!, false, true)),
            encodedInstructionData
        )

        print(incrementInstruction)

//    // Build transaction message
//    val incrementAmount = 5
//    val incrementCounterMessage =
//        Message.Builder()
//            .addInstruction(
//                incrementInstruction
//            )
//            .setRecentBlockhash(blockhashResponse.result!!.blockhash)
//            .build()

////    // Construct the Transaction object from the message
////    val unsignedIncrementTx = Transaction(incrementCounterMessage)

//    // Sign the transaction with some keypair signer
//    val signature = ed25519Signer.signBytes(incrementCounterMessage.serialize())

//    // Signed transaction ready to be submitted to the network
//    val signedTransaction = Transaction(listOf(signature), incrementCounterMessage)

//    val response = rpcClient.sendTransaction(signedTransaction)
//
//    if (response.result) {
//        println("Transaction signature: ${response.result}")
//    } else if (response.error) {
//        println("Failed to send transaction: ${response.error.message}")
//    }

    }


}
