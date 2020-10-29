import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {LoginPageComponent} from './component/login-page/login-page.component';
import {WorkDataPageComponent} from './component/work-data-page/work-data-page.component';
import {SearchPageComponent} from './component/search-page/search-page.component';
import {ChooseSearchTypeComponent} from './component/choose-search-type/choose-search-type.component';
import {AdvancedSearchComponent} from './component/choose-search-type/advanced-search/advanced-search.component';
import {AllMagazinesComponent} from './component/all-magazines/all-magazines.component';


const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginPageComponent},
  {path: 'workData', component: WorkDataPageComponent},
  {path: 'search/:searchType', component: SearchPageComponent},
  {path: 'searchType', component: ChooseSearchTypeComponent},
  {path: 'advancedSearch', component: AdvancedSearchComponent},
  {path: 'chooseMagazines', component: AllMagazinesComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes), FormsModule, CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
