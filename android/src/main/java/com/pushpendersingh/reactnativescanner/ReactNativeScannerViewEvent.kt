package com.pushpendersingh.reactnativescanner

import android.graphics.Point
import android.graphics.Rect
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.uimanager.events.Event
import com.facebook.react.uimanager.events.RCTModernEventEmitter

class ReactNativeScannerViewEvent(
  surfaceId: Int,
  viewId: Int,
  private val rect: Rect,
  private val qrValue: String,
  private val origin: Array<Point>
) : Event<ReactNativeScannerViewEvent>(surfaceId, viewId) {

  override fun getEventName(): String {
    return "topOnQrScanned"
  }

  override fun dispatchModern(rctEventEmitter: RCTModernEventEmitter?) {
    super.dispatchModern(rctEventEmitter) // if we don't call this, the react native part won't receive the event but because of this line event call two times
    rctEventEmitter?.receiveEvent(
      -1,
      viewTag, eventName,
      Arguments.createMap()
    )
  }

  override fun getEventData(): WritableMap {
    val event: WritableMap = Arguments.createMap()
    val bounds = Arguments.createMap()
    bounds.putArray("origin", getPoints(origin))
    bounds.putInt("width", rect.width())
    bounds.putInt("height", rect.height())

    event.putMap("bounds", bounds)
    event.putString("data", qrValue)
    return event
  }

  fun getPoints(points: Array<Point>): WritableArray {
    val origin: WritableArray = Arguments.createArray()
    for (point in points) {
      val pointData: WritableMap = Arguments.createMap()
      pointData.putInt("x", point.x)
      pointData.putInt("y", point.y)
      origin.pushMap(pointData);
    }
    return origin
  }

}
