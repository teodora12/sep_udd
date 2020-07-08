import {Component, OnInit} from '@angular/core';
import {WorkService} from '../../service/work.service';
import {ActivatedRoute} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';
import {ScientificFieldDTO} from '../model/ScientificFieldDTO';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {

  searchType = 0;
  private searchForm: FormGroup;
  showResults = false;
  works = [];
  scientificFieldsList = [];
  showOptions: false;


  private phraseChecked = false;
  private journalTitle = "";
  private title = "";
  private authors = "";
  private keyTerms = "";
  private scientificFields = "";
  private text = "";
  private sf = "";

  constructor(private workService: WorkService, private route: ActivatedRoute) {


    this.route.params.subscribe(params => {
      this.searchType = params.searchType;
    });

    this.showResults = false;

  }

  ngOnInit() {

    this.createForm();
    console.log(this.searchType);
    this.showResults = false;

    this.workService.getScientificFields().subscribe(
      res => {
        console.log(res);
        this.scientificFieldsList = res;
      },
      error => {
        console.log(error);
      }
    );

  }

  createForm() {
    this.searchForm = new FormGroup({
      journalTitle: new FormControl(''),
      title: new FormControl(''),
      authors: new FormControl(''),
      keyTerms: new FormControl(''),
      scientificFields: new FormControl('')
    });
  }

  downloadWork(id) {

    // console.log('preuzmi rad id: ' + id);
    //
    // this.workService.downloadRad(id).subscribe(
    //   res => {
    //     var blob = new Blob([res], {type: 'application/pdf'});
    //     var url= window.URL.createObjectURL(blob);
    //     window.open(url, "_blank");
    //   }, err => {
    //     alert("Error while download file");
    //   }
    // );
  }

  // kupiRad(radId){
  //   console.log('kupi rad id: ' + radId);

  //   this.radService.kupiRad(radId).subscribe(
  //     res => {
  //       alert('Uspesno ste kupili rad');
  //       window.location.href = 'search/' + this.tipPretrage;
  //     }, err => {
  //       alert("Error");
  //     }
  //   );
  // }

  search() {
    if (this.searchType == 1) {

      let phrase = 0;
      if (this.phraseChecked) {
        phrase = 1;
      }

      this.workService.search(this.journalTitle, phrase, "journalTitle").subscribe(
        res => {
          console.log(res);
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    } else if (this.searchType == 2) {

      let phrase = 0;
      if (this.phraseChecked) {
        phrase = 1;
      }

      this.workService.search(this.title, phrase, "title").subscribe(
        res => {
          console.log(res);
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    } else if (this.searchType == 3) {

      let phrase = 0;
      if (this.phraseChecked) {
        phrase = 1;
      }

      this.workService.search(this.authors, phrase, "authors").subscribe(
        res => {
          console.log(res);
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    } else if (this.searchType == 4) {

      let phrase = 0;
      if (this.phraseChecked) {
        phrase = 1;
      }

      this.workService.search(this.keyTerms, phrase, "keyTerms").subscribe(
        res => {
          console.log(res);
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    } else if (this.searchType == 5) {

      let phrase = 0;
      if (this.phraseChecked) {
        phrase = 1;
      }

      this.workService.search(this.text, phrase, 'text').subscribe(
        res => {
          console.log(res);
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    } else if (this.searchType == 6) {

      let temp = "";

      for (let i = 0; i < this.scientificFieldsList.length; i++) {
        temp = temp.concat(this.scientificFields);
        temp = temp.concat(', ');
      }

      if (temp == "") {
        alert("Odaberite naucne oblasti");
        return;
      }

      temp = temp.substring(0, temp.length - 1);
      console.log(temp);

      this.workService.search(temp, 0, "scientificField").subscribe(
        res => {
          this.works = res;
          this.showResults = true;
        },
        error => {
          console.log(error);
        }
      )

    }

  }
}
