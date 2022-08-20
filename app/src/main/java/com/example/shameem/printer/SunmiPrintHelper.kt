package com.example.shameem.printer

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.sunmi.peripheral.printer.*

class SunmiPrintHelper {

    companion object {
        var NoSunmiPrinter = 0x00000000
        var CheckSunmiPrinter = 0x00000001
        var FoundSunmiPrinter = 0x00000002
        var LostSunmiPrinter = 0x00000003
        val instance = SunmiPrintHelper()
    }

    private var checkFoundDevice = false
    /**
     * Checking the printer connection status
     */
    var sunmiPrinter = CheckSunmiPrinter

    /**
     * SunmiPrinterService for API
     */
    var sunmiPrinterService: SunmiPrinterService? = null
    private val innerPrinterCallback: InnerPrinterCallback = object : InnerPrinterCallback() {

        override fun onConnected(service: SunmiPrinterService) {
            sunmiPrinterService = service
            checkSunmiPrinterService(service)
        }

        override fun onDisconnected() {
            sunmiPrinterService = null
            sunmiPrinter = LostSunmiPrinter
        }

    }

    /**
     * init sunmi print service
     */
    fun initSunmiPrinterService(context: Context?) {
        try {
            val ret = InnerPrinterManager.getInstance().bindService(
                context,
                innerPrinterCallback
            )
            if (!ret) {
                sunmiPrinter = NoSunmiPrinter
            }
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }

    /**
     * deInit sunmi print service
     */
    fun deInitSunmiPrinterService(context: Context?) {
        try {
            if (sunmiPrinterService != null) {
                InnerPrinterManager.getInstance().unBindService(context, innerPrinterCallback)
                sunmiPrinterService = null
                sunmiPrinter = LostSunmiPrinter
            }
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
    }



    /**
     * Check the printer connection,
     * like some devices do not have a printer but need to be connected to the cash drawer through a print service
     */
    private fun checkSunmiPrinterService(service: SunmiPrinterService) {

        try {
            checkFoundDevice = InnerPrinterManager.getInstance().hasPrinter(service)
        } catch (e: InnerPrinterException) {
            e.printStackTrace()
        }
        sunmiPrinter = if (checkFoundDevice) FoundSunmiPrinter else NoSunmiPrinter
    }

    /**
     * is Device paired by starting the printer service
     */
    fun isDevicePaired():Boolean{
        return checkFoundDevice
    }

    /**
     * Some conditions can cause interface calls to fail
     * For example: the version is too low、device does not support
     * You can see [ExceptionConst]
     * So you have to handle these exceptions
     */
    private fun handleRemoteException(e: RemoteException) {
        Log.d("handleRemoteException", e.message?:"")
    }

    /**
     * send esc cmd
     */
    fun sendRawData(data: ByteArray?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.sendRAWData(data, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Printer cuts paper and throws exception on machines without a cutter
     */
    fun cutpaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.cutPaper(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Initialize the printer
     * All style settings will be restored to default
     */
    fun initPrinter() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printerInit(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * paper feed three lines
     * Not disabled when line spacing is set to 0
     */
    private fun print3Line() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.lineWrap(3, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }//TODO Service disconnection processing

    /**
     * Get printer serial number
     */
    val printerSerialNo: String
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerSerialNo
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }//TODO Service disconnection processing

    /**
     * Get device model
     */
    val deviceModel: String
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerModal
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }//TODO Service disconnection processing

    /**
     * Get firmware version
     */
    val printerVersion: String
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            sunmiPrinterService!!.printerVersion
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }//TODO Service disconnection processing

    /**
     * Get paper specifications
     */
    val printerPaper: String
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            ""
        } else try {
            if (sunmiPrinterService!!.printerPaper == 1) "58mm" else "80mm"
        } catch (e: RemoteException) {
            handleRemoteException(e)
            ""
        }


    /**
     * Set printer alignment
     */
    fun setAlign(align: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.setAlignment(align, null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Due to the distance between the paper hatch and the print head,
     * the paper needs to be fed out automatically
     * But if the Api does not support it, it will be replaced by printing three lines
     */
    fun feedPaper() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.autoOutPaper(null)
        } catch (e: RemoteException) {
            print3Line()
        }
    }

    /**
     * print text
     * setPrinterStyle Api require V4.2.22 or later, So use esc cmd instead when not supported
     * More settings reference documentation [WoyouConsts]
     */
    fun printText(content: String?, size: Float, isBold: Boolean, isUnderLine: Boolean) {
        if (sunmiPrinterService == null) {
            return
        }
        try {
            try {
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_BOLD,
                    if (isBold) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
            } catch (e: RemoteException) {
                if (isBold) {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOn(), null)
                } else {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.boldOff(), null)
                }
            }
            try {
                sunmiPrinterService!!.setPrinterStyle(
                    WoyouConsts.ENABLE_UNDERLINE,
                    if (isUnderLine) WoyouConsts.ENABLE else WoyouConsts.DISABLE
                )
            } catch (e: RemoteException) {
                if (isUnderLine) {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null)
                } else {
                    sunmiPrinterService!!.sendRAWData(ESCUtil.underlineOff(), null)
                }
            }
            sunmiPrinterService!!.printTextWithFont(content, null, size,null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * print Bar Code
     */
    fun printBarCode(data: String?, symbology: Int, height: Int, width: Int, textposition: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printBarCode(data, symbology, height, width, textposition, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * print Qr Code
     */
    fun printQr(data: String?, modulesize: Int, errorlevel: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printQRCode(data, modulesize, errorlevel, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Print a row of a table
     */
    fun printTable(txts: Array<String?>?, width: IntArray?, align: IntArray?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.printColumnsString(txts, width, align, null)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Print pictures and text in the specified orde
     * After the picture is printed,
     * the line feed output needs to be called,
     * otherwise it will be saved in the cache
     * In this example, the image will be printed because the print text content is added
     */
    fun printBitmap(bitmap: Bitmap?, orientation: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            if (orientation == 0) {
                sunmiPrinterService!!.printBitmap(bitmap, null)
            } else {
                sunmiPrinterService!!.printBitmap(bitmap, null)
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }//TODO Service disconnection processing

    /**
     * Gets whether the current printer is in black mark mode
     */
    val isBlackLabelMode: Boolean
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            false
        } else try {
            sunmiPrinterService!!.printerMode == 1
        } catch (e: RemoteException) {
            false
        }//TODO Service disconnection processing

    /**
     * Gets whether the current printer is in label-printing mode
     */
    val isLabelMode: Boolean
        get() = if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            false
        } else try {
            sunmiPrinterService!!.printerMode == 2
        } catch (e: RemoteException) {
            false
        }



    /**
     * Open cash box
     * This method can be used on Sunmi devices with a cash drawer interface
     * If there is no cash box (such as V1、P1) or the call fails, an exception will be thrown
     *
     * Reference to https://docs.sunmi.com/general-function-modules/external-device-debug/cash-box-driver/}
     */
    fun openCashBox() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.openDrawer(null)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * LCD screen control
     * @param flag 1 —— Initialization
     * 2 —— Light up screen
     * 3 —— Extinguish screen
     * 4 —— Clear screen contents
     */
    fun controlLcd(flag: Int) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.sendLCDCommand(flag)
        } catch (e: RemoteException) {
            handleRemoteException(e)
        }
    }

    /**
     * Display text SUNMI,font size is 16 and format is fill
     * sendLCDFillString(txt, size, fill, callback)
     * Since the screen pixel height is 40, the font should not exceed 40
     */
    fun sendTextToLcd() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.sendLCDFillString("SUNMI", 16, true, object : InnerLcdCallback() {
                @Throws(RemoteException::class)
                override fun onRunResult(show: Boolean) {
                    //TODO handle result
                }
            })
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Display two lines and one empty line in the middle
     */
    fun sendTextsToLcd() {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            val texts = arrayOf("SUNMI", null, "SUNMI")
            val align = intArrayOf(2, 1, 2)
            sunmiPrinterService!!.sendLCDMultiString(texts, align, object : InnerLcdCallback() {
                @Throws(RemoteException::class)
                override fun onRunResult(show: Boolean) {
                    //TODO handle result
                }
            })
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    /**
     * Display one 128x40 pixels and opaque picture
     */
    fun sendPicToLcd(pic: Bitmap?) {
        if (sunmiPrinterService == null) {
            //TODO Service disconnection processing
            return
        }
        try {
            sunmiPrinterService!!.sendLCDBitmap(pic, object : InnerLcdCallback() {
                @Throws(RemoteException::class)
                override fun onRunResult(show: Boolean) {
                    //TODO handle result
                }
            })
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }


    /**
     * Used to report the real-time query status of the printer, which can be used before each
     * printing
     */
    fun showPrinterStatus(application: Application):Boolean {
        if (sunmiPrinterService == null) {
            application.showToastMessage("Service disconnected. Please try again")
            instance.initSunmiPrinterService(application)
            return false
        }
        try {
            when (sunmiPrinterService!!.updatePrinterState()) {
                2 ->{
                    application.showToastMessage("printer found but still initializing")
                    return false
                }
                3 -> {
                    application.showToastMessage("printer hardware interface is abnormal and needs to be reprinted")
                    return false
                }
                4 -> {
                    application.showToastMessage("printer is out of paper")
                    return false
                }
                5 -> {
                    application.showToastMessage("printer is overheating")
                    return false
                }
                6 ->{
                    application.showToastMessage("printer's cover is not closed")
                    return false
                }
                7 ->{
                    application.showToastMessage("printer's cutter is abnormal")
                    return false
                }
                8 -> {
                    application.showToastMessage("printer's cutter is normal")
                    return false
                }
                9 -> {
                    application.showToastMessage("not found black mark paper")
                    return false
                }
                505 -> {
                    application.showToastMessage("printer does not exist")
                    return false
                }
                else -> {
                    return true
                }
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
            application.showToastMessage("printer is write failed")
            return false
        }

    }


    private fun Application.showToastMessage(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}