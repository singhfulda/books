/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CatComponentsPage, CatDeleteDialog, CatUpdatePage } from './cat.page-object';

const expect = chai.expect;

describe('Cat e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let catUpdatePage: CatUpdatePage;
    let catComponentsPage: CatComponentsPage;
    let catDeleteDialog: CatDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Cats', async () => {
        await navBarPage.goToEntity('cat');
        catComponentsPage = new CatComponentsPage();
        expect(await catComponentsPage.getTitle()).to.eq('golaApp.cat.home.title');
    });

    it('should load create Cat page', async () => {
        await catComponentsPage.clickOnCreateButton();
        catUpdatePage = new CatUpdatePage();
        expect(await catUpdatePage.getPageTitle()).to.eq('golaApp.cat.home.createOrEditLabel');
        await catUpdatePage.cancel();
    });

    it('should create and save Cats', async () => {
        const nbButtonsBeforeCreate = await catComponentsPage.countDeleteButtons();

        await catComponentsPage.clickOnCreateButton();
        await promise.all([catUpdatePage.setNameInput('name'), catUpdatePage.setPriceInput('5'), catUpdatePage.setAuthorInput('author')]);
        expect(await catUpdatePage.getNameInput()).to.eq('name');
        expect(await catUpdatePage.getPriceInput()).to.eq('5');
        expect(await catUpdatePage.getAuthorInput()).to.eq('author');
        await catUpdatePage.save();
        expect(await catUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await catComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Cat', async () => {
        const nbButtonsBeforeDelete = await catComponentsPage.countDeleteButtons();
        await catComponentsPage.clickOnLastDeleteButton();

        catDeleteDialog = new CatDeleteDialog();
        expect(await catDeleteDialog.getDialogTitle()).to.eq('golaApp.cat.delete.question');
        await catDeleteDialog.clickOnConfirmButton();

        expect(await catComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
