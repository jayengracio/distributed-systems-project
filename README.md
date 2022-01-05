# Internet Explorers

### How to use

#### To run a service, simply run the following command in the project root directory.

```bash
$ mvn compile exec:java -pl <insert-service-here> -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*
```