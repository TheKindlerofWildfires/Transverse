package transverse

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer

class Texture(filename: String) {
    private var id: Int = 0
    private var width: Int = 0
    private var height: Int = 0

    init {
        val bi: BufferedImage
        try {
            bi = ImageIO.read(File(filename))
            width = bi.width
            height = bi.height

            var pixels_raw = IntArray(width * height * 4)
            pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width)

            val pixels = BufferUtils.createByteBuffer(width * height * 4)

            for (i in 0 until width) {
                for (j in 0 until height) {
                    val pixel = pixels_raw[i * width + j]
                    pixels.put((pixel shr 16 and 0xFF).toByte())//R
                    pixels.put((pixel shr 8 and 0xFF).toByte()) //G
                    pixels.put((pixel shr 0 and 0xFF).toByte()) //B
                    pixels.put((pixel shr 24 and 0xFF).toByte())//A
                }
            }
            pixels.flip()
            id = glGenTextures()
            glBindTexture(GL_TEXTURE_2D, id)
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat())
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST.toFloat())

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun bind() {

        glBindTexture(GL_TEXTURE_2D, id)
    }
}
