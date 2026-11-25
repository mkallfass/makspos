package de.mkallfass.makspos.rest.endpoint

import de.mkallfass.makspos.test.ItDataDirectory
import io.quarkus.test.common.TestResourceScope
import io.quarkus.test.common.WithTestResource
import io.quarkus.test.junit.QuarkusIntegrationTest

@QuarkusIntegrationTest
@WithTestResource(value = ItDataDirectory::class, scope = TestResourceScope.MATCHING_RESOURCES)
class OrdersIT : OrdersTest()
