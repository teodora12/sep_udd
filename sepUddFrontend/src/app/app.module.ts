import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {NavbarComponent} from './component/navbar/navbar.component';
import {LoginPageComponent} from './component/login-page/login-page.component';
import {ToastrModule} from 'ng6-toastr-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {WorkDataPageComponent} from './component/work-data-page/work-data-page.component';
import {SearchPageComponent} from './component/search-page/search-page.component';
import {ChooseSearchTypeComponent} from './component/choose-search-type/choose-search-type.component';
import {AdvancedSearchComponent} from './component/choose-search-type/advanced-search/advanced-search.component';
import { AllMagazinesComponent } from './component/all-magazines/all-magazines.component';
import {CanActivateService} from "./service/security/can-activate.service";
import {TokenInterceptorService} from "./service/security/token-interceptor";
import { ViewWorksPageComponent } from './component/all-magazines/view-works-page/view-works-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginPageComponent,
    WorkDataPageComponent,
    SearchPageComponent,
    ChooseSearchTypeComponent,
    AdvancedSearchComponent,
    AllMagazinesComponent,
    ViewWorksPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule
  ],
  providers: [CanActivateService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
