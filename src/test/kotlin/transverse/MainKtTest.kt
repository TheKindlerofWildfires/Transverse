package transverse

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun mainTest() {
        try {
            main(arrayOf())
        } catch (e: Exception) {
            fail<Nothing>("Smoke test failed: Something is very wrong with your configuration!")
        }
    }
}
