package com.mycompany.app.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.CurrencySpawner;
import com.mycompany.app.Interfaces.Shooter;
import com.mycompany.app.model.entities.Character;
import com.mycompany.app.model.entities.Currency;
import com.mycompany.app.model.entities.CurrencyCharacter;
import com.mycompany.app.model.entities.Enemy;
import com.mycompany.app.model.entities.Entity;
import com.mycompany.app.model.entities.EntityCfg;
import com.mycompany.app.model.entities.GameConfig;
import com.mycompany.app.model.entities.Projectile;

public class Game {

    private boolean Playing;
    private boolean Paused;
    private boolean Over = false;
    public int currency;
    private float elapsedTime = 0f;

    private final Lawn lawn = new Lawn(5, 8);

    private final List<Character> characters = new ArrayList<>();
    private final List<Enemy> enemys = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Currency> collectable_currencys = new ArrayList<>();

    private EntityFactory entityFactory;
    private EnemySpawner enemySpawner;


    private final List<CharacterSeed> characterSeeds = new ArrayList<>();
    
    private CurrencySpawner currencyspawner = new CurrencySpawner();

    private long lastUpdateTime;
    // private void startGame() {
    //     Playing = true;
    //     lastUpdateTime = System.nanoTime();
    //     while (Playing) {
    //         long currentTime = System.nanoTime();
    //         double deltaTime = (currentTime - lastUpdateTime) / 1_000_000_000.0; // till sekunder
    //         lastUpdateTime = currentTime;

    //         if (!Paused && !Over) {
    //             updateGameState(deltaTime);
    //         }
    //     }
    // }
    public Game(GameConfig gcfg) {
        // Lägg till initiala enemys och characters här om det behövs
        this.currency = 200;
        this.entityFactory = new EntityFactory(gcfg);
        this.enemySpawner=new EnemySpawner(this.entityFactory,Difficulty.EASY);
        for(EntityCfg cfg : gcfg.characters){
            this.characterSeeds.add(new CharacterSeed(cfg));
        }

        
        //this.characters.add(new Character("asd",asd,asd,asd,asd))
    }

    private void endGame() {
        
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
        Enemy z = this.enemySpawner.update((float)deltaTime, (float)800.0,gridY, tileH, this.getLawn().getRows());
        

        //System.out.println(deltaTime+" "+800+" "+gridY+" "+tileH+" "+this.getLawn().getRows());
        if(z!=null){
            addEnemy(z);
        }
        if(!Over){
            elapsedTime += deltaTime;
        }
        
        //System.out.println(+enemys.size()+collecable_currencys.size()+projectiles.size());
        
        //System.out.println("characters "+ characters.size());
    }

    
    private void updateDeathCheck() {
        // Tar bort döda entities från listorna
        characters.removeIf(character -> !character.isAlive());
        enemys.removeIf(enemy -> !enemy.isAlive());
        projectiles.removeIf(projectile -> !projectile.isAlive());
        collectable_currencys.removeIf(s -> !s.isAlive());
    }



    private void removeEntity(Entity e) {
        if (e instanceof Enemy)
            //enemys.remove(e);
            e=null;
        else if (e instanceof Character)
           ((Character) e).removeFromTile();
        else if (e instanceof Projectile)
            //projectiles.remove(e);
        e = null;
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

    public void addEnemy(Enemy enemy){
        enemys.add(enemy);
        addEntity(enemy);
    }

    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
        addEntity(projectile);
    }


    public void placeCharacter(int characterseedIndex,Tile tile, int row, int col,float x, float y) {
        CharacterSeed p =getCharacterSeed(characterseedIndex);

        if(p==null){
            System.out.print("No characterseed selected");
        }
        else{
            
            
            Character newCharacter = this.entityFactory.createCharacter(p.type, x, y, row, col);
            
            if(newCharacter==null){
                System.out.print("Character error, character does not exist");
            }
            else if(this.currency<newCharacter.getCurrencyCost()){
                System.out.println("Not enough currency for "+newCharacter.getName()+", current:"+this.currency+" "+newCharacter.getCurrencyCost());
            }
            else if (!this.getCharacterSeed(characterseedIndex).ready_to_place()) {
                System.out.print(newCharacter.getName()+" Is not ready yet,"+getCharacterSeed(characterseedIndex).cooldown_left+" seconds left");
            }   
            else if(tile.is_occupied()){
                System.out.print("That tile is already occupied");
            }
            else{
                this.characters.add(newCharacter);
                currency -= newCharacter.getCurrencyCost();
                addEntity(newCharacter);
                tile.place(newCharacter);
                this.getCharacterSeed(characterseedIndex).try_place();
                System.out.println("Placed "+newCharacter.getName()+" at row " + row + ", col " + col);
            }
        }
        
        
    }

    private void addEntity(Entity e) {
        e.setDeathListener(() -> {
            // entities.remove(e);
            removeEntity(e);
        });
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

    public List<Currency> getCurrencys(){
        return this.collectable_currencys;
    }
    public int get_current_currency(){
        return this.currency;
    }

    //Funktion för att försöka kollekta sol, iterarar över varje sol och kollar ifall musen är på rätt plats
    //Vector2 strulade så jag konverterade det till point istället
    public void collect_currency(Point mouse){
        System.out.print(mouse);
        for(Currency s :this.collectable_currencys){
            System.out.print(s.getHitBox().getMinX());
            if(s.getHitBox().contains(mouse)){
                currency+=s.get_currency_value();
                //"döda" solen
                s.takeDamage(1000);
                System.out.print("du collectade");
            }
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

    public Lawn getLawn() {
        return this.lawn;
    }

}
