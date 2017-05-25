package com.example.chennasreenu.findintruderapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraModule extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView_cam;
    private SurfaceHolder surfaceHolder_cam;
    private Camera camera_cam;
    boolean isPreviewRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_module);
        surfaceView_cam=(SurfaceView)findViewById(R.id.surfaceView_camera_module);
        surfaceHolder_cam=surfaceView_cam.getHolder();
        surfaceHolder_cam.addCallback(this);
        surfaceHolder_cam.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder_cam.setKeepScreenOn(true);

    }

    public static Camera getCameraInstance(int i){
        Camera c = null;
        try {
            c = Camera.open(i);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return c;
    }


    private void stopCamera(){
        if(camera_cam!=null){
            camera_cam.stopPreview();
            camera_cam.release();
            camera_cam=null;
        }
    }

    @Override
    protected void onPause() {
        stopCamera();
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int i = findFrontFacingCamera();

        if(i > 0) ;
        while(true) {
            try {
                camera_cam = getCameraInstance(i);
                try {
                    camera_cam.setPreviewDisplay(holder);
                    return;
                }catch(IOException localIOException2) {
                    stopCamera();
                    return;
                }
            }
            catch(RuntimeException localRuntimeException) {
                localRuntimeException.printStackTrace();
                if(camera_cam == null) continue;

                stopCamera();

                camera_cam=getCameraInstance(i);
                try {
                    camera_cam.setPreviewDisplay(holder);
                    return;
                }catch(IOException localIOException1) {
                    stopCamera();
                    localIOException1.printStackTrace();
                    return;
                }

            } catch(Exception localException) {
                if(this.camera_cam != null) stopCamera();
                localException.printStackTrace();
                return;
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(isPreviewRunning){
            camera_cam.stopPreview();
        }
        if(camera_cam!=null) {
            Camera.Parameters parameters = camera_cam.getParameters();
            camera_cam.setParameters(parameters);
            camera_cam.startPreview();
            isPreviewRunning=true;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            camera_cam.takePicture(null,null,mPictureCallback);
                        }
                    },
                    1000
            );
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopCamera();
    }

    private int findFrontFacingCamera() {
        int camera_count = Camera.getNumberOfCameras();
        for(int j = 0 ; ; j++) {
            if(j >= camera_count) return -1;
            Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(j, localCameraInfo);
            if(localCameraInfo.facing == 1) return j;
        }
    }


    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {


                stopCamera();

                try {
                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 500, 300, true);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int newWidth = 500*2;
                    int newHeight = 300*2;

                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;

                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    matrix.postRotate(-90);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    File f = new File(Environment.getExternalStorageDirectory() + File.separator + "intruder.jpg");
                    System.out.println("File F : " + f);
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setResult(585);
                finish();
            }
        }

    };
}
