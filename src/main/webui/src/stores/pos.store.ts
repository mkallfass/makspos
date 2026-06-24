import { defineStore } from 'pinia'
import type { LineItem, Order, OrderStatistics } from '@/models/pos.model'

interface State {
  lineitems: LineItem[]
  total: number
  givenAmount: number
  orderStatistics: OrderStatistics
}

export const usePosStore = defineStore('posStore', {
  state: (): State =>
    ({
      lineitems: [] as LineItem[],
      total: 0,
      orderStatistics: {
        orderCount: 0,
        overallRevenue: 0,
        productStatistics: []
      } as OrderStatistics
    }) as State,
  getters: {
    cartLineItems(state) {
      // Calculate line item
      let cartTotal: number = 0
      for (const l of state.lineitems) {
        if (l.quantity) {
          l.total = l.quantity * l.price
          cartTotal = cartTotal + l.total
        } else {
          l.quantity = 0
          l.total = 0
        }
      }
      state.total = cartTotal
      return state.lineitems.filter((item) => item.quantity > 0)
    },
    returnAmount(state) {
      if (state.givenAmount > 0) {
        return state.givenAmount - state.total
      } else {
        return 0
      }
    }
  },
  actions: {
    fetchProducts() {
      fetch('/api/products')
        .then((response) => {
          if (response.ok) {
            console.info('Got products successfully from API')
          } else {
            console.error('Could not get products from API: ' + response)
            throw new Error(response.statusText)
          }
          return response.json()
        })
        .then((json) => {
          this.$patch({ lineitems: json })
        })
        .catch((error) => {
          console.error('Error loading products:', error)
          return error
        })
    },
    order() {
      const order = { lineitems: this.cartLineItems, total: this.total } as Order
      fetch('/api/orders', {
        method: 'POST',
        body: JSON.stringify(order),
        headers: {
          'Content-Type': 'application/json; charset=UTF-8',
          Accept: 'application/json; charset=UTF-8'
        }
      })
        .then((response) => {
          if (response.ok) {
            console.info('Order successfully posted to API: ' + JSON.stringify(order))
          } else {
            console.error('Could not post order to API: ' + response)
          }
        })
        .catch((error) => {
          console.error('Error while post order to API: ' + error)
        })
      this.$reset()
    },
    fetchStatistics() {
      fetch('/api/orders/statistics')
        .then((response) => {
          if (response.ok) {
            console.info('Got statistics successfully from API')
          } else {
            console.error('Could not get statistics from API: ' + response)
            throw new Error(response.statusText)
          }
          return response.json()
        })
        .then((json) => {
          this.$patch({ orderStatistics: json })
        })
        .catch((error) => {
          console.error('Error loading statistics:', error)
          return error
        })
    },
    $reset() {
      this.fetchProducts()
      this.total = 0
      this.givenAmount = 0
    }
  }
})
