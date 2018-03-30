package com.nexus.user.status;

import com.nexus.user.status.ui.onboarding.OnboardingPresenterImpl;
import com.nexus.user.status.ui.views.OnboardingView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OnboardingUnitTest {

    @Mock
    OnboardingView mOnboardingView;

    @Mock
    private OnboardingPresenterImpl mOnboardingPresenter;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mOnboardingPresenter = new OnboardingPresenterImpl(mOnboardingView);
    }

    @Test
    public void testServerLoginSuccess() {
        mOnboardingPresenter.onLoginSuccess();
    }

    @After
    public void tearDown() throws Exception {
        mOnboardingPresenter.onDetach();
    }
}