package com.nexus.user.status;

import com.nexus.user.status.ui.UserPresenterImpl;
import com.nexus.user.status.ui.views.UserView;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {

    @Mock
    UserView mMockUserView;

    @Mock
    private UserPresenterImpl mUserPresenterImpl;

    @BeforeClass
    public static void onlyOnce() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        mUserPresenterImpl = new UserPresenterImpl(mMockUserView);
    }

    @Test
    public void fetchUsersTest() {
        mUserPresenterImpl.fetchUsers();
    }

    @After
    public void tearDown() throws Exception {
        mUserPresenterImpl.onDetach();
    }
}