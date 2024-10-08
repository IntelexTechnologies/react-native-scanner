import React, { forwardRef, useCallback, useImperativeHandle, useRef, useEffect } from 'react';
import type { HostComponent } from 'react-native';

import ReactNativeScannerView, { Commands } from './ReactNativeScannerViewNativeComponent';
import type { NativeProps } from './ReactNativeScannerViewNativeComponent';
import type { ScannerViewProps, ScannerViewQRScanEvent } from "./ScannerViewTypes";

const ScannerViewComponent = forwardRef<{}, ScannerViewProps>(({
  pauseAfterCapture = false,
  isActive = false,
  onQrScanned: onQrScannedProp,
  ...otherProps
}, ref) => {

  const scannerViewRef = useRef<React.ComponentRef<HostComponent<NativeProps>> | null>(null);

  useImperativeHandle(ref, () => ({
    pausePreview: () => scannerViewRef.current && Commands.pausePreview(scannerViewRef.current),
    resumePreview: () => scannerViewRef.current && Commands.resumePreview(scannerViewRef.current),
    startScanning: () => scannerViewRef.current && Commands.startScanning(scannerViewRef.current),
    stopScanning: () => scannerViewRef.current && Commands.stopScanning(scannerViewRef.current),
  }), [scannerViewRef])

  useEffect(() => {
    const localRef = scannerViewRef.current;
    return () => {
      // Somehow when the Scanner Component unmounts, the Native Module is not deallocated and keeps on running in the background state
      // So manually stopping the camera to avoid this situation.
      if (localRef) {
        Commands.stopScanning(localRef);
      }
    };
  }, []);

  const onQrScanned = useCallback((event: ScannerViewQRScanEvent) => {
    onQrScannedProp?.(event);
  }, [onQrScannedProp]);

  const scannerView = <ReactNativeScannerView
    {...otherProps}
    key="scannerViewKey"
    ref={scannerViewRef}
    pauseAfterCapture={pauseAfterCapture}
    isActive={isActive}
    onQrScanned={onQrScanned}
  />

  return scannerView;
});

export default ScannerViewComponent;