import { Component, OnInit } from '@angular/core';
import { CartService } from '../../shared-service/cart.service';
import { Cart } from '../../cart';

@Component({
  selector: 'app-add-to-cart',
  templateUrl: './add-to-cart.component.html',
  styleUrls: ['./add-to-cart.component.css']
})
export class AddToCartComponent implements OnInit {
private cart:Cart;

  constructor(private _cartService:CartService) { }

  ngOnInit() {
    this.cart =this._cartService.getCart();

  }

}
