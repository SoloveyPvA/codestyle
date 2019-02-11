Custom check modules for checkstyle

See: http://checkstyle.sourceforge.net/writingchecks.html

Modules: 
* **EmptyLineBeforeReturnCheck**
	* checks for empty lines before `return` or `throw` if the number of lines in the code block is greater than three;
	* parent module: `TreeWalker`.

##Usage
###Requirements
* install and init Gradle 4+ for this project (or use gradlew)
* required Checkstyle version 6.19 (JDK 7)
* install and configure lombok
* build project and install artifact to local repository
* add a required module to your Checkstyle configuration (`xml`), for example, <br>
	* `<module name="EmptyLineBeforeReturnCheck"/>`;<br>
	* with enable auto inserting before return:
	```
		<module name="EmptyLineBeforeReturnCheck">
			<property name="autoFixing" value="true"/>
		</module>
	```
	Attention! Run checkstyle plugin before commit for autofix.

* Add `codestyles` as dependency to your `maven-checkstyle-plugin` plugin, for example in Maven,
```
<plugins>
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-checkstyle-plugin</artifactId>
		<version>2.7</version>
		<dependencies>
			<dependency>
				<groupId>ru.pva</groupId>
				<artifactId>codestyles</artifactId>
				<version>0.1.0</version>
			</dependency>
		</dependencies>
	</plugin>
</plugins>
```
* Run `maven-checkstyle-plugin` (see: https://maven.apache.org/plugins/maven-checkstyle-plugin/)
* Run with CheckStyle-IDEA:
	* add custom module jar to plugin class path in `Third-Party Checks` section;
	* import your configuration file with custom modules;
	* enable check in runtime and before commit.
	
##Development
* For building project use command: gradlew clean build or use Intellij IDEA run configuration "build" (require installed gradle);
* For running test use command: gradlew clean test or use Intellij IDEA run configuration "test"(require installed gradle);