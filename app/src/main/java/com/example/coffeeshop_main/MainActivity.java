package com.example.coffeeshop_main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;
    private MaterialButton buttonOnboardingSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);
        buttonOnboardingSkip = findViewById(R.id.buttonOnboardingSkip);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()) {
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    finish();
                }
            }
        });

        buttonOnboardingSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                finish();
            }
        });

    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemHello = new OnboardingItem();
        itemHello.setTitle("Привет!");
        itemHello.setDescription("Coffee toGo – это приложение в котором вы можете заказать кофе онлайн и забрать его в близжайшей к вам кофейне.\n" +
                "Сейчас расскажем как оно работает");
        itemHello.setImage(R.drawable.hello);

        OnboardingItem itemSearching = new OnboardingItem();
        itemSearching.setTitle("Поиск кофейни");
        itemSearching.setDescription("На карте указаны близжайшие к вам кофейни, выбирайте наиболее удобную для вас. Приложение подскажет как долго до неё идти.");
        itemSearching.setImage(R.drawable.searching);

        OnboardingItem itemOrdering = new OnboardingItem();
        itemOrdering.setTitle("Оформление заказа");
        itemOrdering.setDescription("Выбирайте свои любимые напитки и десерты. Вы можете изменить их состав и выбрать время, когда вам будет удобно их забрать.");
        itemOrdering.setImage(R.drawable.ordering);

        OnboardingItem itemReceiving = new OnboardingItem();
        itemReceiving.setTitle("Получение заказа");
        itemReceiving.setDescription("В указанное время приходите в кофейню и наслаждайтесь вкусом кофе, без очереди и ожидания.");
        itemReceiving.setImage(R.drawable.receiving);

        onboardingItems.add(itemHello);
        onboardingItems.add(itemSearching);
        onboardingItems.add(itemOrdering);
        onboardingItems.add(itemReceiving);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnboardingIndicators() {

        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 0, 0);
        for (int i = 0; i < indicators.length; i++) {

            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);

        }

    }

    private void setCurrentOnboardingIndicator(int index){

        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){

            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i == index){

                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));

            } else {

                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));

            }

        }

        buttonOnboardingSkip.setText("Пропустить");

        if(index == onboardingAdapter.getItemCount() - 1) {

            buttonOnboardingAction.setText("Начать");

        } else {

            buttonOnboardingAction.setText("Дальше");

        }

    }

}