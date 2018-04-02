# Project
Build an Android Java based client app to get the list of users and update their roles and status

### Available features

- App connects to server to fetch data and update the list

- After getting data from server, we go ahead and fetch roles for the users

- When we get the data from server we update the roles with equivalent name

- After updating the role of user we listen to socket to get the status of the users so we update them on the list

- Process date from sockets and update the UI

---
- Added ripple effect tapping on list, good for user feedback (Material design)
- Customized Toolbar to have custom Titles

#### Libraries used

- Android Recyclerview
- Android cardView to showing each item of Recyclerview
- Data Binding for syncing data with adapters
- Glide to load images
- Retrofit to fetch data from server
- RxJava2 to for async tasks and listeners
- Square OkHttp3 for websocket communication
- EventBus for communication between threads
