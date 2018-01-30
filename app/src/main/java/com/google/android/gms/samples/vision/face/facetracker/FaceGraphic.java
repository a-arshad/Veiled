/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * Graphic instance for drawing smiley faces on renders of a face
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int COLOR_CHOICES[] = {
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.MAGENTA,
        Color.RED,
        Color.WHITE,
        Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;


    private Paint mFacePaintOuter;
    private Paint mFacePaint;

    private volatile Face mFace;
    private int mFaceId;

    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;

        mFacePaintOuter = new Paint();
        mFacePaintOuter.setStyle(Paint.Style.STROKE);
        mFacePaintOuter.setStrokeWidth(BOX_STROKE_WIDTH);

        mFacePaint= new Paint();
        mFacePaint.setColor(Color.YELLOW);
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);


        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);

        canvas.drawCircle(x, y, xOffset , mFacePaint);
        canvas.drawCircle(x, y, xOffset , mFacePaintOuter);



        canvas.drawCircle(x - xOffset/2, y, xOffset/10, new Paint());
        canvas.drawCircle(x + xOffset/2, y, xOffset/10, new Paint());



        canvas.drawOval(x - xOffset/2, (float) (y + yOffset/3.5), x + xOffset/2, (float) (y + yOffset/1.9), new Paint());
        canvas.drawOval((float)(x - xOffset/1.75), y + yOffset/4, (float) (x + xOffset/1.75), (float) (y + yOffset/2.2), mFacePaint);

    }


}
