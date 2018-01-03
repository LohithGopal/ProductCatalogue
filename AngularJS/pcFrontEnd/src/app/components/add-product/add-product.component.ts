import { Component, OnInit } from '@angular/core';
import { Product } from '../../product';
import { ProductService } from '../../shared-service/product.service';
import { error } from 'selenium-webdriver';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
private product:Product = new Product();


  constructor(private _productService:ProductService) { }

  ngOnInit() {
    
  }

  updateProduct(product){
    if(this.product.productId == undefined ){
    this._productService.setter(this.product);
    this._productService.createProduct(this.product)
    .subscribe((product)=>{
      console.log(product);
    }, (error)=>{
      console.log(error);
    }
    )
   }else{
      this._productService.setter(this.product);
      this._productService.updateProduct(this.product)
      .subscribe((product)=>{
      console.log(product);
      }, (error)=>{
      console.log(error);
      }
      )
    }

  }


  deleteProduct(product){
    this._productService.setter(product);
    this._productService.deleteProduct(product.productId).subscribe((data)=>{
      console.log(product);
    },(error)=>{
      console.log(error);
    });
    
    
  }



}
