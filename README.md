# PHPBB Parser
[![Build Status](https://travis-ci.org/lukemaxwell/phpbb-parser.png?branch=master)](https://travis-ci.org/lukemaxwell/phpbbb-parser)
[![codecov](https://codecov.io/gh/lukemaxwell/phpbb-parser/branch/master/graph/badge.svg)](https://codecov.io/gh/lukemaxwell/phpbb-parser)

A Java parser for PHPBB-based websites.

The `PostsPageParser` can be used to parse a page of posts. It will automatically extract the posts, along with the thread ID and total post count for the thread.

## Usage
Import the `PostsPageParser`  and use the `parse()` method on a string containing 
the full page HTML.
 
```java
import xyz.codepunk.phpbbparser.models.PostsPage;
import xyz.codepunk.phpbbparser.PostsPageParser;
import xyz.codepunk.phpbbparser.exceptions.ParserException;


public class Main {


    public static void main(String[] args) throws ParserException, IOException, InterruptedException {
        final String html = "<html>.....</html>"; // Update with real HTML
        PostsPage page = PostsPageParser.parse(html);
        System.out.println(page.threadId);
    }
}
```

## Installation
Clone the repository and add the jar file as a project dependency. An easy way to do so when using gradle is to
copy it to a libs folder.


Clone the phpbb-parser repository:
```
$ git clone git@github.com:lukemaxwell/phpbb-parser.git
```

Add a libs folder to project root:
```
$ cd projectname
$ mkdir libs
```

Add the libs folder to gradle dependencies by editing build.gradle:
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
}
```


Copy the jar to the `libs` directory:
```
$ cd projectname
$ cp ../phpbb-parser/build/libs/phpbb-parser-1.0.SNAPSHOT.jar libs/
```
     
## Site Coverage

The `PostsPageParser` has been tested against the following URLs:

 * https://forum.openoffice.org/en/forum/viewtopic.php?f=5&t=102832

