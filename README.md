# CodeDoesGood Android Style Guide

In order for our volunteers to be able to easily jump between projects and also to facilitate easy code review we are strict 
about having our platform style guides followed. We are a professional group of developers and one of our main purposes to is 
train up new developers. We believe that part of being an effective team member and a high quality developer is creating high 
quality, readable, consistent code. We believe following a style guide is an important part of being a developer.

There are additional things covered by Google's [Code Style for Contributors](https://source.android.com/source/code-style) 
if you are interested in how to format your code. What is listed below however are the only things that we are asking all 
devs to do the same. We find large style guides difficult to follow and mostly unnecessary and so we have only included what 
we consider the most important for a readable codebase with consistent formatting.
 
[Java](#java)

[Documentation](#documentation)

[Logging guidelines](#logging-guidelines)

[File Naming](#file-naming) 

[Field definition and naming](#field-definition-and-naming)

[Treat acronyms as words](#treat-acronyms-as-words)

[Avoid justifying variable declarations](#avoid-justifying-variable-declarations)

[Line-wrapping strategies](#line-wrapping-strategies)

[XML style rules](#xml-style-rules)

[Tests style rules](#tests-style-rules)

[Gradle Style](#gradle-style)

[Private Keys](#private-keys)

[Miscellaneous - Project Specific Recommendations](#miscellaneous---project-specific-recommendations)

## Lead Mentors
(please delete this section when you copy the style guide into your project)  

This style guide should be copied into the wiki for each new project either by the Lead Mentor or by someone the Lead Mentor assigns this task to. One of our guiding principles is to demonstrate distributed authority and teach our volunteers autonomy. What this means to us is that as long as our volunteers adhere to the processes and policies we do set, they have the freedom to make decisions. We know that every developer has a preferred way of working and coding and we do not want to get in the way of your efficiency (hey, another one of our guiding principles is to make efficient use of volunteer time!) and for that reason we want our Lead Mentors to be able to set the style guidelines for their own project. 

Some guidelines are required, some are preferred, and some are optional. Guidelines which are not required will be under the heading **Project-Specific** may be changed by the Lead Mentor either at their own discretion or through consensus with their team. Please note guidelines which are marked as _"Preferred"_ really are preferred and we would prefer you change them only if you have a good reason...but you are free to change them if you wish. Please delete the word "Preferred" from the ones that are labelled preferred so that your Hatchlings do not get confused and think they do not need to follow those guidelines.

\* With optional and preferred guidelines there is actually one requirement. The requirement is that you _must_ list a guideline for that item. No guidelines may be removed, but they can be edited. Note: You are more than welcome to add additional guidelines if there are things that are important to you which we have not included.

## Java
We are a Java-first dev group. This means: Use Google's coding standards for source code in the Java™ Programming Language. [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

## Documentation
Android projects should be self-documenting but sometimes documentation is required.

- The code is a work-around for a bug (please include the URL of where you found the work around if you found it online)
- A large chunk of code has been copied from another project or article (please include the URL and other identifying information of where the work comes from as well as the License if it comes from a hosted repository / not an article or Stack Overflow post)
- You are doing anything that may seem out of the ordinary / not a native or standard approach

### Organization
Source files should be packaged by feature, not by layer or category. Packaging by feature results in better modularity and easier code navigation as related items will be closer together.

Do:
```java
org.codedoesgood.myapp.sampledetails.view.SampleDetailsActivity
org.codedoesgood.myapp.sampledetails.view.SampleDetailsAdapter
org.codedoesgood.myapp.sampledetails.model.SampleItem
org.codedoesgood.myapp.sampledetails.viewmodel.SampleDetailsViewModel
org.codedoesgood.myapp.samplelist.view.SampleListActivity
org.codedoesgood.myapp.samplelist.view.SampleListAdapter
org.codedoesgood.myapp.samplelist.model.SampleItemRow
org.codedoesgood.myapp.samplelist.viewmodel.SampleListViewModel
```

Don't:
```java
org.codedoesgood.myapp.adapters.SampleDetailsAdapter
org.codedoesgood.myapp.adapters.SampleListAdapter
org.codedoesgood.myapp.models.SampleItem
org.codedoesgood.myapp.models.SampleItemRow
org.codedoesgood.myapp.views.SampleDetailsActivity
org.codedoesgood.myapp.views.SampleListActivity
org.codedoesgood.myapp.viewmodels.SampleDetailsViewModel
org.codedoesgood.myapp.viewmodels.SampleListViewModel
```

- Commented out code must include a // TODO: statement explaining why it is commented out
- There is no single correct solution for this but using a logical and consistent order will improve code learnability and readability. It is recommendable to use the following order:

1. Constants
2. Fields
3. Constructors
4. Override methods and callbacks (public or private)
5. Public methods
6. Private methods
7. Inner classes or interfaces

Example:

```java
public class MainActivity extends Activity {

    public static final String CONSTANT = "CONSTANT"
    
    private String title;
    private TextView textViewTitle;

    @Override
    public void onCreate() {
    }

    public void setTitle(String title) {
      this.title = title;
    }
    
    private void setUpView() {
    }
    
    static class AnInnerClass {
       ...
    }
    
    interface SomeInterface {
    }
}
```

If your class is extending an __Android component__ such as an Activity or a Fragment, it is a good practice to order the override methods so that they __match the component's lifecycle__. For example, if you have an Activity that implements `onCreate()`, `onDestroy()`, `onPause()`, `onStart()`, `onStop()` and `onResume()`, then the correct order is:

```java
public class MainActivity extends Activity {

	//Order matches Activity lifecycle
    @Override
    public void onCreate() {}
    
    @Override
    public void onStart() {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}
    
    @Override
    public void onStop() {}

    @Override
    public void onDestroy() {}

}
```
#### Parameter ordering in methods

When programming for Android, it is quite common to define methods that take a `Context`. If you are writing a method like this, then the __Context__ must be the __first__ parameter.

The opposite case are __callback__ interfaces that should always be the __last__ parameter.

Examples:

```java
// Context always goes first
public User loadUser(Context context, int userId);

// Callbacks always go last
public void loadUserAsync(Context context, int userId, UserCallback callback);
```

In general, though, if you need to write documentation to explain what a function does, that code is not written well. 
Do not optimize unnecessarily or prematurely, but rewrite your code to be as clear and obviously self-evident as you can. 
If you are not sure how to fix it talk to another person on your team or post the snippet in your platform channel 
on Slack for help. Usually it can be resolved by encapsulating parts of the function into smaller single-purpose functions.

Please do not leave code commented out without adding a `TODO://` statement which explains why it is commented out. 
If we find code commented without a TODO statement we will be contacting the dev who wrote it to ask why.

#### Project-Specific Recommendations

\* If you are working on an API that will be consumed by other projects or turned into a framework/library, additional documentation will be required. Your Lead Mentor will adjust this style guide in that scenario with more information specific to your project. Lead Mentors please delete this line when creating your project style guide.


## Logging guidelines

Use the logging methods provided by the `Log` class to print out error messages or other information that may be useful for developers to identify issues:

* `Log.v(String tag, String msg)` (verbose)
* `Log.d(String tag, String msg)` (debug)
* `Log.i(String tag, String msg)` (information)
* `Log.w(String tag, String msg)` (warning)
* `Log.e(String tag, String msg)` (error)

As a general rule, we use the class name as tag and we define it as a `static final` field at the top of the file. For example:

```java
public class MyClass {
    private static final String TAG = MyClass.class.getSimpleName();

    public myMethod() {
        Log.e(TAG, "My error message");
    }
}
```

VERBOSE and DEBUG logs __must__ be disabled on release builds. It is also recommended to disable INFORMATION, WARNING and ERROR logs but you may want to keep them enabled if you think they may be useful to identify issues on release builds. If you decide to leave them enabled, you have to make sure that they are not leaking private information such as email addresses, user ids, etc.

To only show logs on debug builds:

```java
if (BuildConfig.DEBUG) Log.d(TAG, "The value of x is " + x);
```

#### Project-Specific Recommendations
- Lead Mentors: If you prefer your Logging could be also handled by the library [Timber](https://github.com/JakeWharton/timber).


## File Naming
#### Class Files

Any classes that you define should be named using UpperCamelCase, for example:

	AndroidActivity, NetworkHelper, UserFragment, PerActivity

Any classes extending an Android framework component should **always** end with the component name. For example:

	UserFragment, SignUpActivity, RateAppDialog, PushNotificationServer, NumberView
 
#### Resource Files

When naming resource files you should be sure to name them using lowercase letters and underscores instead of spaces, for example:

	activity_main, fragment_user, item_post
 
#### Drawable Files

Drawable resource files should be named using the **ic_** prefix along with the size and color of the asset. For example, white accept icon sized at 24dp would be named:

	ic_accept_white_24dp

And a black cancel icon sized at 48dp would be named:

	ic_cancel_black_48dp
 
Other drawable files should be named using the corresponding prefix, for example:

| Type       | Prefix    | Example                |
|------------|-----------|------------------------|
| Selector   | selector_ | selector_button_cancel |
| Background | bg_       | bg_rounded_button      |
| Circle     | circle_   | circle_white           |
| Progress   | progress_ | progress_circle_purple |
| Divider    | divider_  | divider_grey           |

When creating selector state resources, they should be named using the corresponding suffix:

| State    | Suffix    | Example             |
|----------|-----------|---------------------|
| Normal   | _normal   | btn_accept_normal   |
| Pressed  | _pressed  | btn_accept_pressed  |
| Focused  | _focused  | btn_accept_focused  |
| Disabled | _disabled | btn_accept_disabled |
| Selected | _selected | btn_accept_selected |

#### Layout Files

When naming layout files, they should be named starting with the name of the Android Component that they have been created for. For example:

| Component        | Class Name      | Layout Name       |
|------------------|-----------------|-------------------|
| Activity         | MainActivity    | activity_main     |
| Fragment         | MainFragment    | fragment_main     |
| Dialog           | RateDialog      | dialog_rate       |
| Widget           | UserProfileView | view_user_profile |
| AdapterView Item | N/A             | item_follower     |


## Field definition and naming

All fields should be declared at the top of the file, following these rules:

- Private, non-static field names should not start with m. This is right:

    userSignedIn, userNameText, acceptButton

Not this:

    mUserSignedIn, mUserNameText, mAcceptButton


- Private, static field names do not need to start with an s. This is right:

    someStaticField, userNameText

Not this:

	sSomeStaticField, sUserNameText


- All other fields also start with a lower case letter.


    int numOfChildren;
    String username;


- Static final fields (known as constants) are ALL_CAPS_WITH_UNDERSCORES.


    private static final int PAGE_COUNT = 0;

Field names that do not reveal intention should not be used. For example,

    int e; //number of elements in the list

why not just give the field a meaningful name in the first place, rather than leaving a comment!

    int numberOfElements;
    
## Treat acronyms as words

Any acronyms for class names, variable names etc should be treated as words - this applies for any capitalisation used for any of the letters. For example:

| Do              | Don't           |
|-----------------|-----------------|
| setUserId       | setUserID       |
| String uri      | String URI      |
| int id          | int ID          |
| parseHtml       | parseHTML       |
| generateXmlFile | generateXMLFile |
 
## Avoid justifying variable declarations

Any declaration of variables should not use any special form of alignment, for example:

This is fine:

    private int userId = 8;
    private int count = 0;
    private String username = "hitherejoe";

Avoid doing this:

    private String username = "hitherejoe";
    private int userId      = 8;
    private int count       = 0;

This creates a stream of whitespace which is known to make text difficult to read for certain learning difficulties.

## Line-wrapping strategies
There isn't an exact formula that explains how to line-wrap and quite often different solutions are valid. However there are a few rules that can be applied to common cases.

__Break at operators__

When the line is broken at an operator, the break comes __before__ the operator. For example:

```java
int longName = anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne
        + theFinalOne;
```

__Assignment Operator Exception__

An exception to the `break at operators` rule is the assignment operator `=`, where the line break should happen __after__ the operator.

```java
int longName =
        anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne + theFinalOne;
```

__Method chain case__

When multiple methods are chained in the same line - for example when using Builders - every call to a method should go in its own line, breaking the line before the `.`

```java
Picasso.with(context).load("http://ribot.co.uk/images/sexyjoe.jpg").into(imageView);
```

```java
Picasso.with(context)
        .load("http://ribot.co.uk/images/sexyjoe.jpg")
        .into(imageView);
```

__Long parameters case__

When a method has many parameters or its parameters are very long, we should break the line after every comma `,`

```java
loadPicture(context, "http://ribot.co.uk/images/sexyjoe.jpg", mImageViewProfilePicture, clickListener, "Title of the picture");
```

```java
loadPicture(context,
        "http://ribot.co.uk/images/sexyjoe.jpg",
        mImageViewProfilePicture,
        clickListener,
        "Title of the picture");
```

## XML style rules
#### Use self closing tags

When an XML element doesn't have any contents, you __must__ use self closing tags.

This is good:

```xml
<TextView
	android:id="@+id/text_view_profile"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content" />
```

This is __bad__ :

```xml
<!-- Don\'t do this! -->
<TextView
    android:id="@+id/text_view_profile"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" >
</TextView>
```
#### Attributes ordering
Ordering attributes not only looks tidy but it helps to make it quicker when looking for attributes within layout files. As a general rule,

1. View Id
2. Style
3. Layout width and layout height
4. Other `layout_` attributes, sorted alphabetically
5. Remaining attributes, sorted alphabetically

For example:

    <Button
        android:id="@id/button_accept"
        style="@style/ButtonStyle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentStart="true"
        android:padding="16dp"
        android:text="@string/button_skip_sign_in"
        android:textColor="@color/bluish_gray" />

Note: This formatting can be carried out by using the format feature in android studio -

`cmd + shift + L`

Doing this makes it easy to navigate through XML attributes when it comes to making changes to layout files.

## Tests style rules
### Unit tests

Any Unit Test classes should be written to match the name of the class that the test are targeting, followed by the Test suffix. For example:

| Class                | Test Class               |
|----------------------|--------------------------|
| DataManager          | DataManagerTest          |
| UserProfilePresenter | UserProfilePresenterTest |
| PreferencesHelper    | PreferencesHelperTest    |

All Test methods should be annotated with the `@Test` annotation, the methods should be named using the following template:


    @Test
    public void methodNamePreconditionExpectedResult() { }

So for example, if we want to check that the signUp() method with an invalid email address fails, the test would look like:


    @Test
    public void signUpWithInvalidEmailFails() { }

Tests should focus on testing only what the method name entitles, if there’s extra conditions being tested in your Test method then this should be moved to it’s own individual test.

If a class we are testing contains many different methods, then the tests should be split across multiple test classes - this helps to keep the tests more maintainable and easier to locate. For example, a DatabaseHelper class may need to be split into multiple test classes such as :


    DatabaseHelperUserTest
    DatabaseHelperPostsTest
    DatabaseHelperDraftsTest

### Espresso tests

Each Espresso test class generally targets an Activity, so the name given to it should match that of the targeted Activity, again followed by Test. For example:

| Class                | Test Class               |
|----------------------|--------------------------|
| MainActivity         | MainActivityTest         |
| ProfileActivity      | ProfileActivityTest      |
| DraftsActivity       | DraftsActivityTest       |

When using the Espresso API, methods should be chained on new lines to make the statements more readable, for example:


    onView(withId(R.id.text_title))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

Chaining calls in this style not only helps us stick to less than 100 characters per line but it also makes it easy to read the chain of events taking place in espresso tests.

### Robot Pattern 

Libraries like Espresso allow UI tests to have stable interactions with your app, but without discipline, these tests can become hard to manage and require frequent updating. To deal with this issue we strongly recommend to use the robot pattern that allows you to create stable, readable, and maintainable tests. Here is a great talk about the [Robot Pattern](https://news.realm.io/news/kau-jake-wharton-testing-robots/)

## Gradle Style
### Dependencies

### Versioning

Where applicable, versioning that is shared across multiple dependencies should be defined as a variable within the project's build.gradle scope. For example:

    Project's build.gradle:
    
    ext {
	    // default dependencies versions
	    support = '25.3.1'
	    okHttp = '3.6.0'
	    dagger = '2.10'
	    retrofit = '2.2.0'
    }
    
    App's buld.gradle:
    
    final versions = rootProject.ext

    dependencies {

	    //App Dependencies
	    compile "com.android.support:appcompat-v7:$versions.support"
	    compile "com.android.support:recyclerview-v7:$versions.support"
	    compile "com.android.support:cardview-v7:$versions.support"
	    compile "com.android.support:support-annotations:$versions.support"
	    compile "com.android.support:design:$versions.support"
	    .....
    }

This makes it easy to update dependencies in the future as we only need to change the version number once for multiple dependencies.

### Grouping

Where applicable, dependencies should be grouped by package name, with spaces in-between the groups. For example:


    compile "com.android.support:percent:$versions.support"
    compile "com.android.support:customtabs:$versions.support"

    compile 'io.reactivex:rxandroid:1.2.0'
    compile 'io.reactivex:rxjava:1.1.5'

    compile 'com.jakewharton:butterknife:7.0.1'
    compile 'com.jakewharton.timber:timber:4.1.2'

    compile 'com.github.bumptech.glide:glide:3.7.0'


`compile` , `testCompile` and `androidTestCompile`  dependencies should also be grouped into their corresponding section. For example:

    // App Dependencies
    compile "com.android.support:support-v4:$versions.support"
    compile "com.android.support:recyclerview-v7:$versions.support"

    // Instrumentation test dependencies
    androidTestCompile "com.android.support:support-annotations:$versions.support"

    // Unit tests dependencies
    testCompile 'org.robolectric:robolectric:3.0'

###  Independent Dependencies

Where dependencies are only used individually for application or test purposes, be sure to only compile them using `compile` , `testCompile` or `androidTestCompile` . For example, where the robolectric dependency is only required for unit tests, it should be added using:

    testCompile 'org.robolectric:robolectric:3.0'

## Private Keys
As we know, we can not upload our private keys to public repositories.
In order to manage this issue we will handle this by saving our private keys in two different ways:
 1) Save the private keys in the environment variable.
 2) Save the private keys in the gradle.properties file (this file is never uploaded to repository, it will be in each developer's machine).

Then we can access the private key in the `build.gradle`:

    defaultConfig {
      manifestPlaceholders = 
          [facebookAppId: project.hasProperty("'FACEBOOK_API'") ? FACEBOOK_APP_ID: System.getenv('FACEBOOK_API')]
    }

As we use the Travis-CI we need to save also our private keys in the environment variable in order to build and test our application. Here is a how-to: https://stackoverflow.com/questions/34247645/how-to-mock-gradles-buildconfigfield-for-travis-ci-build?answertab=votes#tab-top

## Miscellaneous - Project Specific Recommendations
### Compress image resources
When needed to use png or jpg images always try to compress the files before copy into project. You could use this website for this purpose [TinyPNG](https://tinypng.com/).

### Avoid using ENUMS
Always try to use TypeDef instead of ENUMS in order to increase the app performace. Good article to read about it: [Android Performance: Avoid using ENUM on Android](https://android.jlelse.eu/android-performance-avoid-using-enum-on-android-326be0794dc3)

### Be carefull with Memory Leak
We need to avoid memory leaks while developing Android Apps, here a good article about it [Eight Ways Your Android App Can Leak Memory](http://blog.nimbledroid.com/2016/05/23/memory-leaks.html).Be aware while using `Asynctask` [The Hidden Pitfalls of AsyncTask](http://blog.danlew.net/2014/06/21/the-hidden-pitfalls-of-asynctask/).

### Do not block the UI Thread
We need to avoid blocking the UI Thread while running heavy work. So before to perform any heavy task like `Networking Jobs`, `Long Computations` or `Database Operations` we need to use background jobs [Best Practices for Background Jobs](https://developer.android.com/training/best-background.html).

### Avoid using Magic Numbers
A magic number is a direct usage of a number in the code. Avoiding usage of magic numbers improves readability of the code and makes code easier to maintain. Imagine setting the size of the password field in the GUI. If a magic number is used, whenever the max size changes, the code would have to be changed in two different locations. If one is forgetten, this will lead to inconsistencies.

### Avoid using hard-coded xml values
Use values in resource files instead. Along the same vein as avoiding magic numbers, as well as a couple other benefits. Lessens memory footprint by creating one reference point for potentially numerous places where a value may be used. Uses memory only when needed.

### Favor verbosity over brevity
Give descriptive names for variables to avoid having to tack on additional comments explaining the code. This leads to self-documenting code that is easier to parse through and understand.

##### Credits:  
- [Ribot: android-guidelines](https://github.com/ribot/android-guidelines/blob/master/project_and_code_guidelines.md)
- [Commit451: android-project-guidelines](https://github.com/Commit451/guidelines/blob/master/android.md)
- [Marcio Aguiar: Stack Overflow - What is a Magic number and why is it bad](http://stackoverflow.com/questions/47882/what-is-a-magic-number-and-why-is-it-bad)
