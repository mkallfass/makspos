import { defineStore } from 'pinia'
import type { LineItem } from "@/models/cart.model";
import type { ProductSelection } from "@/models/product.model";

interface State {
  lineItems: Map<number, LineItem>
  total: number
}

export const useCartStore = defineStore('cartStore', {
    state: () : State => ({
        lineItems: new Map<number, LineItem>(),
        total: 0
    }),
  actions: {
      addLineItem(item : ProductSelection) {
      if (!item) {
        return
      }
      if (!this.lineItems) {
        this.lineItems = new Map<number, LineItem>()
      }
      if (item.quantity < 1) {
        this.removeItem(item.id)
        return
      }

      // Calculate lineitem
      let lineItem : LineItem = item as  LineItem
      lineItem.total = lineItem.quantity * lineItem.price
      // Add calculated lineitem
      this.lineItems.set(lineItem.id, lineItem)
      // Calculate total
      let cartTotal: number = 0
      for (let i of this.lineItems.values()) {
        cartTotal = cartTotal + i.total
      }
      this.total = cartTotal
    },
    removeItem(id: number) {
      if (this.lineItems) {
        this.lineItems.delete(id)
      }
    }
  }
})
