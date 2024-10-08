package com.pushpendersingh.reactnativescanner

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.common.MapBuilder
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.ReactNativeScannerViewManagerDelegate
import com.facebook.react.viewmanagers.ReactNativeScannerViewManagerInterface

@ReactModule(name = ReactNativeScannerViewManager.NAME)
class ReactNativeScannerViewManager(private val mCallerContext: ReactApplicationContext) :
  SimpleViewManager<ReactNativeScannerView>(), ReactNativeScannerViewManagerInterface<ReactNativeScannerView?> {

  private val mDelegate: ViewManagerDelegate<ReactNativeScannerView>

  init {
    mDelegate = ReactNativeScannerViewManagerDelegate(this)
  }

  override fun getDelegate(): ViewManagerDelegate<ReactNativeScannerView> {
    return mDelegate
  }

  override fun getName(): String {
    return NAME
  }

  override fun createViewInstance(reactContext: ThemedReactContext): ReactNativeScannerView {
    val scannerView = ReactNativeScannerView(mCallerContext)
    scannerView.setUpCamera()
    return scannerView
  }

  companion object {
    const val NAME = "ReactNativeScannerView"

    const val COMMAND_PAUSE_PREVIEW = 1
    const val COMMAND_RESUME_PREVIEW = 2
    const val COMMAND_START_SCANNING = 3
    const val COMMAND_STOP_SCANNING = 4
  }

  override fun getExportedCustomDirectEventTypeConstants(): Map<String?, Any> {
    return MapBuilder.of(
      "topOnQrScanned",
      MapBuilder.of("registrationName", "onQrScanned")
    )
  }

  override fun getCommandsMap(): MutableMap<String, Int> {
    val map = mutableMapOf<String, Int>()
    map["pausePreview"] = COMMAND_PAUSE_PREVIEW
    map["resumePreview"] = COMMAND_RESUME_PREVIEW
    map["startScanning"] = COMMAND_START_SCANNING
    map["stopScanning"] = COMMAND_STOP_SCANNING
    return map
  }

  override fun receiveCommand(root: ReactNativeScannerView, commandId: String?, args: ReadableArray?) {
    when (commandId) {
      "pausePreview" -> root.pausePreview()
      "resumePreview" -> root.resumePreview()
      "startScanning" -> root.startScanning()
      "stopScanning" -> root.stopScanning()
      else -> {
        println("Unsupported Command")
      }
    }

    super.receiveCommand(root, commandId, args)
  }

  @ReactProp(name = "pauseAfterCapture")
  override fun setPauseAfterCapture(view: ReactNativeScannerView?, value: Boolean) {
    view?.setPauseAfterCapture(value)
  }

  @ReactProp(name = "isActive")
  override fun setIsActive(view: ReactNativeScannerView?, value: Boolean) {
    view?.setIsActive(value)
  }

  override fun pausePreview(view: ReactNativeScannerView?) {
    view?.pausePreview()
  }

  override fun resumePreview(view: ReactNativeScannerView?) {
    view?.resumePreview()
  }

  override fun startScanning(view: ReactNativeScannerView?) {
    view?.startScanning()
  }

  override fun stopScanning(view: ReactNativeScannerView?) {
    view?.stopScanning()
  }
}
