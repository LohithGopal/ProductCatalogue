import { Component } from '@angular/core';
import { ProductService } from './shared-service/product.service';
import { Product } from './product';
import { Router, ActivatedRoute } from '@angular/router';
import { SearchProductsComponent } from './components/search-products/search-products.component';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Product Catalogue';
  private searchString:String;
  private products:Product[];

  constructor(private _productService:ProductService, private _router:Router) { }

  searchClick(searchString){
    console.log("Searching for the String "+ this.searchString );
    this._productService.setSearchString(this.searchString);
    
    this._router.navigate(["/search"]);
    
    
  }

  redirectToRoot(){
    console.log("Resetting to Root");
    this._router.navigate(["/"]);
    
  }


}
