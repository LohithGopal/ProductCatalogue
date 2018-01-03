import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../shared-service/product.service';
import { Product } from '../../product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-products',
  templateUrl: './search-products.component.html',
  styleUrls: ['./search-products.component.css']
})
export class SearchProductsComponent implements OnInit {
  private products:Product[];
  private searchString:String
  constructor(private _productService:ProductService, private _router:Router ) { }

  ngOnInit() {

    this._productService.getProductsOnDesc(this._productService.getSearchString())
    .subscribe((products)=>{
      this.products=products;
      console.log(products);
      
    },  (error)=>{
      console.log(error);
    }
    );
    
  }

  setClickedProduct(selectedRow:number){
    console.log("Clicked row "+ selectedRow);
    console.log("Clicked ProductId "+ this.products[selectedRow].productId);
    this._productService.setSelectedRow(this.products[selectedRow].productId);
    this._router.navigate(["/prddtls"]);
   }
  
  

}


