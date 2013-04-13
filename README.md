### cucumber-testng-factory

The cucumber-testng-factory builder is an attempt to provide a launchpad for Cucumber-JVM from the TestNG based
test framework.

Simply add a testng @Factory method to a class container cucumber annotations and build the factory:

    public class TestFactory {

      @Factory
      public Object[] create() {
        return CucumberFactoryBuilder.create(new File("src"));
      }

      ...
    }

See `src/test/java/com/talios/TestFactory` for an example.

Currently there are several limitations with the factory builder such as not supporting TestNG groups and dependencies,
two things which were the driving reason for attempting to build the bridge.

Hopefully these will come in time.


