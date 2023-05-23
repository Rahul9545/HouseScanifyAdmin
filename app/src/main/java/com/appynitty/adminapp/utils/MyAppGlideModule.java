package com.appynitty.adminapp.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;

import java.nio.ByteBuffer;

public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.prepend(String.class, ByteBuffer.class, new Base64ModelLoaderFactory());
    }
}
