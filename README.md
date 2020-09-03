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
Clone the repository and add the jar file as a project dependency. Below is a Gradle example using a libs folder.


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

The `PostsPageParser` has been confirmed to function correctly for the following sites/URLs:

1. [OpenOffice forum](https://forum.openoffice.org/en/forum/viewtopic.php?f=5&t=102832)
2. [Joomla forum](https://forum.joomla.org/viewtopic.php?f=706&t=976319)
3. [Ubuntu forum](https://forum.ubuntu-it.org/viewtopic.php?f=17&t=637551)
4. [Pixelmator forum](http://www.pixelmator.com/community/viewtopic.php?f=18&t=17730)
5. [VLC forum](https://forum.videolan.org/viewtopic.php?f=12&t=154744)
6. [Virtualbox forum](https://forums.virtualbox.org/viewtopic.php?f=35&t=96608)
7. [Mozillaline](http://forums.mozillazine.org/viewtopic.php?f=7&t=3034017)
8. [Debian forum](http://forums.debian.net/viewtopic.php?f=17&t=147212)
9. [Raspberry Pi forum](https://www.raspberrypi.org/forums/viewtopic.php?f=63&t=187256)
10. [Adblock Plus forum](https://adblockplus.org/forum/viewtopic.php?f=10&t=75244)
11. [Arstechnica forum](https://arstechnica.com/civis/viewtopic.php?f=6&t=1444785)

