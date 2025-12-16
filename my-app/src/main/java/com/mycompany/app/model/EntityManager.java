package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.CurrencySpawner;
import com.mycompany.app.Interfaces.Shooter;
import com.mycompany.app.model.entities.Character;
import com.mycompany.app.model.entities.Currency;
import com.mycompany.app.model.entities.CurrencyCharacter;
import com.mycompany.app.model.entities.Enemy;
import com.mycompany.app.model.entities.EntityCfg;
import com.mycompany.app.model.entities.GameConfig;
import com.mycompany.app.model.entities.Projectile;


public class EntityManager{


    private final List<Character> characters = new ArrayList<>();
    private final List<Enemy> enemys = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Currency> collectable_currencys = new ArrayList<>();
    private final List<CharacterSeed> characterSeeds = new ArrayList<>();
    private final CurrencySpawner currencyspawner = new CurrencySpawner();
    private final EntityFactory entityFactory;
    private final EnemySpawner enemySpawner;
    private final Lawn lawn;

    public EntityManager(GameConfig gcfg,Lawn lawn){ 
        this.entityFactory = new EntityFactory(gcfg);
        this.enemySpawner=new EnemySpawner(this.entityFactory,Difficulty.EASY);
        this.lawn = lawn;
        for(EntityCfg cfg : gcfg.characters){
            this.characterSeeds.add(new CharacterSeed(cfg));
        }
    }


    public void updateGameState(double deltaTime) {
        updateProjectiles(deltaTime);
        updateEnemys(deltaTime);
        updateCharacters(deltaTime);
        updateCharacterSeeds(deltaTime);
        updateCurrencySpawner(deltaTime);
        updateDeathCheck();

        //float tileW = 800 * 0.80f / lawn.getCols();


        //Det här borde nog inte vara här men jag visste inte riktigt hur man skulle få in det från view
        //Innan kördes enemysspawner.update från view, jag flyttade den till modelen(där den borde vara)
        //Men funktionen behöver värden från view 
        //det här är också väldigt oeffektivt(räknar ut samma sak varje frame)
        // /sixten 
        float tileH = 600 * 0.72f / lawn.getRows();
        float gridY = (600 - tileH * lawn.getRows()) / 2f - 50;
        Enemy z = this.enemySpawner.update((float)deltaTime, (float)800.0,gridY, tileH, this.lawn.getRows());
        

                //System.out.println(deltaTime+" "+800+" "+gridY+" "+tileH+" "+this.getLawn().getRows());
        if(z!=null){
            enemys.add(z);
        }
    }

    public  List<CharacterSeed> getcharacterSeeds(){
        return this.characterSeeds;
    }

    public CharacterSeed getCharacterSeed(int index){
            if (index<0 || index>=this.characterSeeds.size()){
            return null;
            }
            return this.characterSeeds.get(index);
    }
    
    private void updateCharacters(double deltaTime) {
        for (Character character : characters) {
            
            character.update(deltaTime);
            if (character instanceof Shooter shooter) { 
                Projectile p = shooter.shoot();
                if (p != null) {
                    addProjectile(p);
                }
            }
            if(character instanceof CurrencyCharacter flower){
                Currency s = flower.spawnCurrency();
                if(s != null){
                    collectable_currencys.add(s);
                }

            }
        }
    }


    private void updateCurrencySpawner(double deltaTime) {
        this.currencyspawner.update(deltaTime);
        Currency s = currencyspawner.spawnCurrency();
        if(s != null){
            collectable_currencys.add(s);
        }
    }

    private void updateEnemys(double deltaTime) {
        for (Enemy enemy : enemys) {
            enemy.update(deltaTime);
            boolean hittatcharactera = false;
            for (Character character : characters) {
                if (enemy.checkCollision(character)) {
                    if (enemy.canEat()) {
                    enemy.eat(character);
                    }
                    hittatcharactera = true;
                }   
            }
                if(!hittatcharactera){
                enemy.move(deltaTime);
                }
        }
    }

    private void updateProjectiles(double deltaTime) {
        for (Projectile projectile : projectiles) {
            projectile.update((float) deltaTime);
            for (Enemy enemy : enemys) {
                if (projectile.checkCollision(enemy)) {
                    projectile.onHit(enemy);
                }
            }
        }
    }
    private void updateCharacterSeeds(double deltaTime) {
        for (CharacterSeed characterseed : this.characterSeeds) {
            characterseed.update((float) deltaTime);
        }
    }

    private void updateDeathCheck() {
        // Tar bort döda entities från listorna
        characters.removeIf(character -> !character.isAlive());
        enemys.removeIf(enemy -> !enemy.isAlive());
        projectiles.removeIf(projectile -> !projectile.isAlive());
        collectable_currencys.removeIf(s -> !s.isAlive());
    }

    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
    }

    public List<Enemy> getEnemys(){
        return this.enemys;
    }
    public List<Character> getCharacters(){
        return this.characters;
    }
    public List<Projectile> getProjectiles(){
        return this.projectiles;
    }
    public Lawn getLawn(){
        return this.lawn;
    }

    public List<Currency> getCurrencys(){
        return this.collectable_currencys;
    }
    public boolean placeCharacter(int characterseedIndex,Tile tile, int row, int col,float x, float y) {
        CharacterSeed p =getCharacterSeed(characterseedIndex);

        if(p==null){
            System.out.print("No characterseed selected");
        }
        else{            
            Character newCharacter = this.entityFactory.createCharacter(p.type, x, y, row, col);
            if(newCharacter==null){
                System.out.print("Character error, character does not exist");
            }
            else if (!this.getCharacterSeed(characterseedIndex).ready_to_place()) {
                System.out.print(newCharacter.getName()+" Is not ready yet,"+getCharacterSeed(characterseedIndex).cooldown_left+" seconds left");
            }   
            else if(tile.is_occupied()){
                System.out.print("That tile is already occupied");
            }
            else{
                this.characters.add(newCharacter);
                tile.place(newCharacter);
                this.getCharacterSeed(characterseedIndex).try_place();
                System.out.println("Placed "+newCharacter.getName()+" at row " + row + ", col " + col);
                return true;
            }
            return false;
        }
        
        return false;
    }

    public boolean checkEnemy(){
        for(Enemy z : this.getEnemys()){
            if(z.getPosition().x <= 0){
                return true;
            }
        }
        return false;
    }
}