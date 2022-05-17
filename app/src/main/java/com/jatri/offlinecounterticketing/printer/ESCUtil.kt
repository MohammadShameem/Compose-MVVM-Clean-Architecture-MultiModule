package com.jatri.offlinecounterticketing.printer

object ESCUtil {
    const val ESC: Byte = 0x1B // Escape
    const val FS: Byte = 0x1C // Text delimiter
    const val GS: Byte = 0x1D // Group separator
    const val DLE: Byte = 0x10 // data link escape
    const val EOT: Byte = 0x04 // End of transmission
    const val ENQ: Byte = 0x05 // Enquiry character
    const val SP: Byte = 0x20 // Spaces
    const val HT: Byte = 0x09 // Horizontal list
    const val LF: Byte = 0x0A //Print and wrap (horizontal orientation)
    const val CR: Byte = 0x0D // Home key
    const val FF: Byte =
        0x0C // Carriage control (print and return to the standard mode (in page mode))
    const val CAN: Byte = 0x18 // Canceled (cancel print data in page mode)


    fun init_printer(): ByteArray {
        val result = ByteArray(2)
        result[0] = ESC
        result[1] = 0x40
        return result
    }

    //打印浓度指令
    fun setPrinterDarkness(value: Int): ByteArray {
        val result = ByteArray(9)
        result[0] = GS
        result[1] = 40
        result[2] = 69
        result[3] = 4
        result[4] = 0
        result[5] = 5
        result[6] = 5
        result[7] = (value shr 8).toByte()
        result[8] = value.toByte()
        return result
    }



    fun nextLine(lineNum: Int): ByteArray {
        val result = ByteArray(lineNum)
        for (i in 0 until lineNum) {
            result[i] = LF
        }
        return result
    }

    // ------------------------style set-----------------------------

    fun setDefaultLineSpace(): ByteArray {
        return byteArrayOf(0x1B, 0x32)
    }


    fun setLineSpace(height: Int): ByteArray {
        return byteArrayOf(0x1B, 0x33, height.toByte())
    }

    // ------------------------underline-----------------------------

    fun underlineWithOneDotWidthOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 1
        return result
    }


    fun underlineWithTwoDotWidthOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 2
        return result
    }


    fun underlineOff(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 45
        result[2] = 0
        return result
    }
    // ------------------------bold-----------------------------

    fun boldOn(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 69
        result[2] = 0xF
        return result
    }


    fun boldOff(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 69
        result[2] = 0
        return result
    }

    // ------------------------character-----------------------------

    fun singleByte(): ByteArray {
        val result = ByteArray(2)
        result[0] = FS
        result[1] = 0x2E
        return result
    }


    fun singleByteOff(): ByteArray {
        val result = ByteArray(2)
        result[0] = FS
        result[1] = 0x26
        return result
    }


    fun setCodeSystemSingle(charset: Byte): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 0x74
        result[2] = charset
        return result
    }


    fun setCodeSystem(charset: Byte): ByteArray {
        val result = ByteArray(3)
        result[0] = FS
        result[1] = 0x43
        result[2] = charset
        return result
    }
    // ------------------------Align-----------------------------

    fun alignLeft(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 0
        return result
    }


    fun alignCenter(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 1
        return result
    }


    fun alignRight(): ByteArray {
        val result = ByteArray(3)
        result[0] = ESC
        result[1] = 97
        result[2] = 2
        return result
    }


    fun cutter(): ByteArray {
        return byteArrayOf(0x1d, 0x56, 0x01)
    }


    fun gogogo(): ByteArray {
        return byteArrayOf(0x1C, 0x28, 0x4C, 0x02, 0x00, 0x42, 0x31)
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////          private                /////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    private fun setQRCodeSize(modulesize: Int): ByteArray {

        val dtmp = ByteArray(8)
        dtmp[0] = GS
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x43
        dtmp[7] = modulesize.toByte()
        return dtmp
    }

    private fun setQRCodeErrorLevel(errorlevel: Int): ByteArray {

        val dtmp = ByteArray(8)
        dtmp[0] = GS
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x45
        dtmp[7] = (48 + errorlevel).toByte()
        return dtmp
    }

    private fun getBytesForPrintQRCode(single: Boolean): ByteArray {

        val dtmp: ByteArray
        if (single) {
            dtmp = ByteArray(9)
            dtmp[8] = 0x0A
        } else {
            dtmp = ByteArray(8)
        }
        dtmp[0] = 0x1D
        dtmp[1] = 0x28
        dtmp[2] = 0x6B
        dtmp[3] = 0x03
        dtmp[4] = 0x00
        dtmp[5] = 0x31
        dtmp[6] = 0x51
        dtmp[7] = 0x30
        return dtmp
    }
}