import { Component, OnInit } from '@angular/core';
import { Product } from '../../product';
import { ProductService } from '../../shared-service/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-products',
  templateUrl: './list-products.component.html',
  styleUrls: ['./list-products.component.css']
})
export class ListProductsComponent implements OnInit {
private products:Product[];
private category:String;

  constructor(private _productService:ProductService, private _router:Router ) {

   }

  ngOnInit() {
    this.getProducts();
  }

  getProducts(){
    this._productService.getProducts().subscribe((products)=>{
    this.products=products;
  },  (error)=>{
    console.log(error);
  }
)

}

setClickedProduct(selectedRow:number){
  console.log("Clicked row "+ selectedRow);
  console.log("Clicked ProductId "+ this.products[selectedRow].productId);
  this._productService.setSelectedRow(this.products[selectedRow].productId);
  this._router.navigate(["/prddtls"]);


  }

}
