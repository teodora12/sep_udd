import { Component, OnInit } from '@angular/core';
import {WorkService} from '../../../service/work.service';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-advanced-search',
  templateUrl: './advanced-search.component.html',
  styleUrls: ['./advanced-search.component.css']
})
export class AdvancedSearchComponent implements OnInit {

  private searchForm: FormGroup;
  showResults = false;
  works: any;
  private scientificFieldsList = [];

  private journalTitleChecked = true;
  private titleChecked = true;
  private authorsChecked = true;
  private keyTermsChecked = true;
  private textChecked = true;

  private phraseJournalTitle = false;
  private phraseTitle = false;
  private phraseAuthors = false;
  private phraseKeyTerms = false;
  private phraseText = false;

  private journalTitle = '';
  private title = '';
  private authors = '';
  private keyTerms = '';
  private scientificFields = '';
  private text = '';

  constructor(private workService: WorkService) { }

  ngOnInit() {

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


  search() {
    let temp = '';

    for (let i = 0; i < this.scientificFields.length; i++) {
      temp = temp.concat(this.scientificFields[i]);
      temp = temp.concat(', ');
    }

    temp = temp.substring(0, temp.length - 1);
    console.log(temp);

    const o = {
      journalTitle: this.journalTitle,
      journalTitleChecked: this.journalTitleChecked,
      title: this.title,
      titleChecked: this.titleChecked,
      authors: this.authors,
      authorsChecked: this.authorsChecked,
      keyTerms: this.keyTerms,
      keyTermsChecked: this.keyTermsChecked,
      text: this.text,
      textChecked: this.textChecked,
      scientificFields: temp,
      phraseJournalTitle: this.phraseJournalTitle,
      phraseTitle: this.phraseTitle,
      phraseAuthors: this.phraseAuthors,
      phraseKeyTerms: this.phraseKeyTerms,
      phraseText: this.phraseText
    }

    console.log(o);

    this.workService.advancedSearch(o).subscribe(
      res => {
        console.log(res);
        this.works = res;
        this.showResults = true;
      },
      error => {
        console.log(error);
      }
    );

  }

  back(){
    this.showResults = false;
  }

  downloadPdf(id: any) {

    console.log('preuzmi rad id: ' + id);

    this.workService.downloadWork(id).subscribe(
      res => {
        var blob = new Blob([res], {type: 'application/pdf'});
        var url= window.URL.createObjectURL(blob);
        window.open(url, "_blank");
      }, err => {
        alert("Error while download file");
      }
    );

    
  }


}
